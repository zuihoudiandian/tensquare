package com.demo.article.controller;

import com.demo.article.pojo.Comment;
import com.demo.article.service.commentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.PipedReader;
import java.util.List;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/4/29.
 * PS: Not easy to write code, please indicate.
 */
@RestController
@RequestMapping("comment")
public class commentController {
    @Autowired
    private commentService commentService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PutMapping("thumbup/{commentId}")
    public Result thumbup(@PathVariable String commentId){
        String userId ="456";
        Object flag = redisTemplate.opsForValue().get("thumbup_" + userId + "_" + commentId);
        if (flag==null){
            commentService.thumbup(commentId);
            redisTemplate.opsForValue().set("thumbup_" + userId + "_" + commentId,1);
            return new Result(true,StatusCode.OK,"成功");
        }
            return new Result(true,StatusCode.REPERROR,"重复点赞");
    }

    @GetMapping("findall")
    public Result findAll(){
        List<Comment> all = commentService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",all);
    }
    @GetMapping("article/{articleId}")
    public Result findByArticleId(@PathVariable String articleId){
        List<Comment> commentList = commentService.findByArticleId(articleId);
        return new Result(true,StatusCode.OK,"成功",commentList);
    }

    @GetMapping("{id}")
    public Result findById(@PathVariable String id){
       Comment comment = commentService.findById(id);
       return new Result(true,StatusCode.OK,"成功",comment);
    }
    @PostMapping("add")
    public Result save(@RequestBody Comment comment){
      commentService.save(comment);
        return new Result(true,StatusCode.OK,"添加成功",comment);
    }
    @PutMapping("{commentId}")
    public Result updateById(@PathVariable String commentId,@RequestBody Comment comment){
        comment.set_id(commentId);
        Comment update = commentService.updateById(comment);
        if (update!=null){
            return new Result(true,StatusCode.OK,"成功",update);
        }
        return new Result(true,StatusCode.OK,"更新数据不存在");
    }
    @DeleteMapping("{commentId}")
    public Result delById(@PathVariable String commentId){
        commentService.deleteById(commentId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
