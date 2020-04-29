package com.demo.article.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.demo.article.mapper.ArticleMapper;
import com.demo.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ArticleService  extends ServiceImpl<ArticleMapper,Article> {

    @Autowired
    private ArticleMapper  articleMapper;

    @Autowired
    private IdWorker idWorker;



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
}
