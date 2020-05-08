package com.tensquare.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//配置Mapper包扫描
@MapperScan("com.tensquare.user.mapper")
@EnableEurekaClient
public class userApplication {
    public static void main(String[] args) {
        SpringApplication.run(userApplication.class, args);
    }

}
