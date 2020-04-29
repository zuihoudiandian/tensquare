package com.demo.article.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import util.IdWorker;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/4/29.
 * PS: Not easy to write code, please indicate.
 */
@Configuration
public class IdWorkerConfig {
    @Bean
    public IdWorker createIdWorker() {
        return new IdWorker(1, 1);
    }
    //spring boot 启动后创建实例。

}
