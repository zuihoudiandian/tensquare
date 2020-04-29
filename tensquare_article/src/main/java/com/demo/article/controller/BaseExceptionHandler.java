package com.demo.article.controller;

import entity.Result;
import entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handler(Exception e) {
        log.error(e.getMessage());
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }

}
