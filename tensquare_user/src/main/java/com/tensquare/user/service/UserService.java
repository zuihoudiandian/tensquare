package com.tensquare.user.service;

import com.tensquare.user.mapper.UserMapper;
import com.tensquare.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/4/30.
 * PS: Not easy to write code, please indicate.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User selectById(String userId){
       return userMapper.selectById(userId);
    }
}
