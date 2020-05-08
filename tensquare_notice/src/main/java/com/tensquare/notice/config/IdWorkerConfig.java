package com.tensquare.notice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import util.IdWorker;

@Configuration
public class IdWorkerConfig {
    @Bean
    public IdWorker createIdWorker() {
        return new IdWorker(1, 1);
    }
    //spring boot 启动后创建实例。

}
