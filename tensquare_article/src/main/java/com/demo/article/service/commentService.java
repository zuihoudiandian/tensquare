package com.demo.article.service;

import com.demo.article.pojo.Comment;
import com.demo.article.repository.commentRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/4/29.
 * PS: Not easy to write code, please indicate.
 */
@Service
public class commentService {
    @Autowired
    private commentRepository commentRepository;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Comment> findAll() {
       return commentRepository.findAll();
    }

    public Comment findById(String id) {
        return commentRepository.findById(id).get();
    }

    public Comment updateById(Comment comment) {
        //save 方法主键相同则进行修改，不通直接新增
        Optional<Comment> byId = commentRepository.findById(comment.get_id());
        if (byId.isPresent()){
            commentRepository.save(comment);
            return comment;
        }
        return null;
    }

    public void save(Comment comment) {
        String id = idWorker.nextId() + "";
        comment.set_id(id);  //不用这个也行 。mongo内置全局唯一ID
        comment.setThumbup(0);//点赞数
        comment.setPublishdate(new Date());
        commentRepository.save(comment);
    }

    public void deleteById(String commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> findByArticleId(String articleId) {
       return commentRepository.findByArticleid(articleId);
    }

    public void thumbup(String commentId,Integer thumbupNumber) {
//        Comment comment = commentRepository.findById(commentId).get();
//        comment.setThumbup(comment.getThumbup()+1);
        //commentRepository.save(comment);
//        上面方案存在线程不安全。
        //redis+zookeeper 分布式锁解决。     volatile 修饰只适合单机。项目集群就无效。
        //下面使用mongo 指令操作字段，因为mongo 是线程安全的
        //点赞功能优化
        //封装修改的条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentId));

        //封装修改的数值
        Update update = new Update();
        //使用inc列值增长
        update.inc("thumbup", thumbupNumber);

        //直接修改数据
        //第一个参数是修改的条件
        //第二个参数是修改的数值
        //第三个参数是MongoDB的集合名称
        mongoTemplate.updateFirst(query, update, "comment");
    }
}
