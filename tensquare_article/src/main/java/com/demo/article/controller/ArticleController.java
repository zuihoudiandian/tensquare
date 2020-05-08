package com.demo.article.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.article.client.NoticeClient;
import com.demo.article.pojo.Article;
import com.demo.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@CrossOrigin  //@CrossOrigin 支持跨域
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisTemplate redisTemplate;


   // 保存用户订阅信息，
    @PostMapping("subscribe")
    public Result subscribe(@RequestBody Map map){
     Boolean flag =  articleService.subscribe(map.get("articleId").toString(),map.get("userId").toString());
      if (flag==true){
          return new Result(true,StatusCode.OK,"订阅成功");
      }else {
          return new Result(true,StatusCode.OK,"取消订阅成功");
      }
    }

    //文章点赞
    @GetMapping("thumbUp/{id}")
    public Result thumbUp(@PathVariable("id") String articleId){
        //通过jwt 拿到userId
        String userId="4";
        //查询文章点赞信息，根据用户id和文章id
        String key="thumbUp_article_"+userId+"_"+articleId;
        Object flag = redisTemplate.opsForValue().get(key);
        if (flag==null){
            //没有用户点赞 文章。
            articleService.thumpUp(articleId,userId);
            redisTemplate.opsForValue().set(key,1);
            return new Result(true,StatusCode.OK,"点赞成功");
        }else {
            return new Result(false,StatusCode.REPERROR,"不能重复点赞");
        }

    }

    @PostMapping("save")
    public Result save(@RequestBody Article article){
        articleService.saveArticle(article);
        return new Result(true,StatusCode.OK,"新增成功");
    }
    @PostMapping("update/{id}")
    public Result updateById(@PathVariable String id,@RequestBody Article article){
        article.setId(id);
        articleService.updateById(article);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    @DeleteMapping("{id}")
    public Result delece(@PathVariable String id ){
        articleService.removeById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    @GetMapping("find")
    public Result findAll() {
        List<Article> list = articleService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
    @GetMapping(value = "{articleId}")
    public Result findById(@PathVariable String articleId) {
        Article article = articleService.findById(articleId);
        return new Result(true, StatusCode.OK, "查询成功", article);
    }
    @PostMapping("search/{page}/{size}")
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer size,
                             @RequestBody Map<String,Object> map){
        IPage<Article> Page = articleService.findByPage(map, page, size);
        PageResult<Article> pageResult =new PageResult<>(Page.getTotal(),Page.getRecords());
        return  new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
}