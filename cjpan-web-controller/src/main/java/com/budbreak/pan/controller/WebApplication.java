package com.budbreak.pan.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description: 启动类
 * @author:
 * @Createed Date: 2020-4-24 14:46:14
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@ComponentScan("com.budbreak.pan")
@MapperScan(basePackages = "com.budbreak.pan.mapper")
@SpringBootApplication
@EnableSwagger2
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
    }
}