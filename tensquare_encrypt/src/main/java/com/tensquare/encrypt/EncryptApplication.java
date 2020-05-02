package com.tensquare.encrypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/4/30.
 * PS: Not easy to write code, please indicate.
 */
@SpringBootApplication
@EnableEurekaClient
//开启zuul网关
@EnableZuulProxy
public class EncryptApplication {
    public static void main(String[] args) {
        SpringApplication.run(EncryptApplication.class,args);
    }
}
