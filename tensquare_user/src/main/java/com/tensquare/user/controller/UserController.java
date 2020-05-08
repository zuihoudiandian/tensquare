package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/4/30.
 * PS: Not easy to write code, please indicate.
 */
@RestController
@RequestMapping("user")
@CrossOrigin  //@CrossOrigin 支持跨域
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/{userId}")
    public Result selectById(@PathVariable String userId){
        User user = userService.selectById(userId);
        return new Result(true, StatusCode.OK,"查询成功",user);
    }
}
