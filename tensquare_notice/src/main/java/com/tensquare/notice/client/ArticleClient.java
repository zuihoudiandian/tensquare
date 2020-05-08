package com.tensquare.notice.client;

import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/5/4.
 * PS: Not easy to write code, please indicate.
 */
@FeignClient("tensquare-article")
public interface ArticleClient {
    @RequestMapping(value = "article/{articleId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("articleId")  String articleId) ;
}
