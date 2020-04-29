package com.demo.article.repository;

import com.demo.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/4/29.
 * PS: Not easy to write code, please indicate.
 */

public interface commentRepository  extends MongoRepository<Comment,String> {
    //根据方法名查询。
    List<Comment> findByArticleid(String articleId);
}
