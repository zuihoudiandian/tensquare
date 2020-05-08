package com.demo.article.client;


import com.demo.article.pojo.Notice;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Author by admin, Email xx@xx.com, Date on 2020/5/7.
 * PS: Not easy to write code, please indicate.
 */
@FeignClient("tensquare-notice")
public interface NoticeClient {
   @PostMapping("notice/save")
    public Result save(@RequestBody Notice notice);

}
