package com.demo.article.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.article.mapper.ArticleMapper;
import com.demo.article.pojo.Article;
import com.demo.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.IdWorker;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


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
    @RequestMapping(value = "{articleId}", method = RequestMethod.GET)
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