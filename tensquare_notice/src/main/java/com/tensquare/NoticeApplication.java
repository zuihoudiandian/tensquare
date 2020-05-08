package com.tensquare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.tensquare.notice.mapper")
public class NoticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoticeApplication.class, args);
    }
}