package com.tensquare.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//配置Mapper包扫描
@MapperScan("com.tensquare.user.mapper")
public class userApplication {
    public static void main(String[] args) {
        SpringApplication.run(userApplication.class, args);
    }

}
