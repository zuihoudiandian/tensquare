package com.tensquare.notice.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/5/4.
 * PS: Not easy to write code, please indicate.
 */
@FeignClient("tensquare-user")
public interface UserClient {
    @GetMapping("user/{userId}")
    public Result selectById(@PathVariable("userId")  String userId);

}
