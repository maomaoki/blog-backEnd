package com.ym.blogBackEnd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.ym.blogBackEnd.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class BlogBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBackEndApplication.class, args);
    }

}
