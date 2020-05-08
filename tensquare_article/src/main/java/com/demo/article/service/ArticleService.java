package com.demo.article.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.demo.article.client.NoticeClient;
import com.demo.article.mapper.ArticleMapper;
import com.demo.article.pojo.Article;
import com.demo.article.pojo.Notice;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;
import entity.ConstantEnum;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ArticleService  extends ServiceImpl<ArticleMapper,Article> {

    @Autowired
    private ArticleMapper  articleMapper;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private NoticeClient noticeClient;

    public List<Article> findAll() {
        return articleMapper.selectList(null);
    }

    public Article findById(String articleId) {
        return articleMapper.selectById(articleId);
    }

    public void saveArticle(Article article) {
        //使用分布式id生成器
        String id = idWorker.nextId() + "";
        article.setId(id);

        //初始化数据
        article.setVisits(0);   //浏览量
        article.setThumbup(0);  //点赞数
        article.setComment(0);  //评论数

        //新增
        articleMapper.insert(article);
        //新增文章后创建消息，通知给订阅者。
        //通过JWT 拿到文章作者信息。假设 article.getUserId=1
        String userid="2";
        article.setUserid(userid);
        //设置作者KEY 存放订阅者ID
        String authorKey = "article_author_"+article.getUserid();
        //拿到redis中作者1的所有订阅者信息。
        Set<String> set = redisTemplate.boundSetOps(authorKey).members();
        if (null!=set && set.size()>0){
            //给订阅者发送消息
            Notice notice =null;
            for (String uid : set) {
                 notice = new Notice();
                //接收消息的用户ID
                notice.setReceiverId(uid);
                //进行操作用户的ID
                notice.setOperatorId(userid);
                //被操作的类型
                notice.setAction("publish");
                //被操作的对象。
                notice.setTargetType("article");
                //被操作文章的ID
                notice.setTargetId(id);
                //通知类型
                notice.setType("sys");
                noticeClient.save(notice);
                //发消息rabbitmq，新消息通知，订阅消息 （交换机，路由键,消息内容）
                rabbitTemplate.convertAndSend("article_subscribe",userid,"文章"+id+"发布了");
            }
        }
    }


    public void deleteById(String articleId) {
        articleMapper.deleteById(articleId);
    }

    public IPage<Article> findByPage(Map<String, Object> map, Integer page, Integer size) {
        //设置查询条件
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        Set<String> keySet = map.keySet();// 遍历KEY 放到set去重
        for (String key : keySet) {
            // if (map.get(key) != null) {
            //     wrapper.eq(key, map.get(key));
            // }

            //第一个参数是否把后面的条件加入到查询条件中
            //和上面的if判断的写法是一样的效果，实现动态sql
            wrapper.eq(map.get(key) != null, key, map.get(key));
        }

        //设置分页参数
        Page<Article> pageData = new Page<>(page, size);

        //执行查询
        //第一个是分页参数，第二个是查询条件
        IPage<Article> Page = articleMapper.selectPage(pageData, wrapper);

        //返回
        return Page;
    }

//    public Boolean subscribe(String articleId, String userId) {
//        //根据文章Id查匈作者Id
//        String authorId = articleMapper.selectById(articleId).getUserid();
//        //设置用户KEY 存放文章作者ID
//        String userKey ="article_subscribe_"+userId;
//        //设置作者KEY 存放订阅者ID
//        String authorKey = "article_author_"+authorId;
//        //查询用户集合中是否有该作者的ID
//        Boolean flag = redisTemplate.boundSetOps(userKey).isMember(articleId);
//        if(flag==true){
//            //订阅该作者，就取消订阅
//            redisTemplate.boundSetOps(userKey).remove(authorId);
//            redisTemplate.boundSetOps(authorKey).remove(userId);
//            //从订阅集合中删除作者ID，从订阅者集合中删除用户ID
//            return false;
//        }else {
//            //没有订阅信息，添加订阅信息。
//            redisTemplate.boundSetOps(userKey).add(authorId);
//            redisTemplate.boundSetOps(authorKey).add(userId);
//        }
//        return true;
//    }
    //上面通过ajax 轮询接口查询msql 实现消息通知
    //下面通过rabbitmq 改进消息通知。

    public Boolean subscribe(String articleId, String userId) {
        //根据文章Id查匈作者Id
        String authorId = articleMapper.selectById(articleId).getUserid();

        //创建Rabbit管理器
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());

        //声明exchange
        DirectExchange exchange = new DirectExchange("article_subscribe");
        rabbitAdmin.declareExchange(exchange);
        //创建queue(名称，是否持久化消息)
        Queue queue = new Queue("article_subscribe_" + userId, true);

        //声明exchange和queue的绑定关系，设置路由键为作者id
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(authorId);

        //设置用户KEY 存放文章作者ID
        String userKey ="article_subscribe_"+userId;
        //设置作者KEY 存放订阅者ID
        String authorKey = "article_author_"+authorId;
        //查询用户集合中是否有该作者的ID
        Boolean flag = redisTemplate.boundSetOps(userKey).isMember(articleId);
        if(flag==true){
            //订阅该作者，就取消订阅
            redisTemplate.boundSetOps(userKey).remove(authorId);
            redisTemplate.boundSetOps(authorKey).remove(userId);
            //从订阅集合中删除作者ID，从订阅者集合中删除用户ID
            //删除绑定的队列
            rabbitAdmin.removeBinding(binding);
            return false;
        }else {
            //没有订阅信息，添加订阅信息。
            redisTemplate.boundSetOps(userKey).add(authorId);
            redisTemplate.boundSetOps(authorKey).add(userId);
            //声明队列和绑定队列
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(binding);
        }
        return true;
    }

    //文章点赞
    public void thumpUp(String articleId,String userId){
        Article article = articleMapper.selectById(articleId);
        article.setThumbup(article.getThumbup()+1);
        articleMapper.updateById(article);
        //点赞通知
        Notice notice = new Notice();
        notice.setReceiverId(article.getUserid());
        notice.setOperatorId(userId);
        notice.setAction(ConstantEnum.publish.toString());
        notice.setTargetType(ConstantEnum.article.toString());
        notice.setTargetId(articleId);
        notice.setType(ConstantEnum.sys.toString());
        //保存消息
        noticeClient.save(notice);

    }
}
