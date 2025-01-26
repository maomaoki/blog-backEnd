package com.ym.blogBackEnd.controller;

import com.ym.blogBackEnd.common.response.BaseResponse;
import com.ym.blogBackEnd.utils.EmailUtils;
import com.ym.blogBackEnd.utils.RedisUtils;
import com.ym.blogBackEnd.utils.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class MainController {


    @Resource
    private EmailUtils emailUtils;


    @Resource
    private RedisUtils redisUtils;

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public BaseResponse<String> health() {
        return ResultUtils.success("ok");
    }


    /**
     * 健康检查 - 邮件
     */
    @GetMapping("/email")
    public BaseResponse<String> email() {
        String to = "546211257@qq.com";
        String subject = "测试邮件";
        String content = "测试邮件内容";
        emailUtils.sendEmail(to, subject, content);
        return ResultUtils.success("ok");
    }


    /**
     * 健康检查 - redis
     */
    @GetMapping("/redis/set")
    public BaseResponse<String> redisSet() {
        redisUtils.setValue("test", "test", 100);
        return ResultUtils.success("ok");
    }

    @GetMapping("/redis/get")
    public BaseResponse<Object> redisGet() {
        return ResultUtils.success(redisUtils.getValue("test"));
    }

    @GetMapping("/redis/delete")
    public BaseResponse<Boolean> redisDelete() {
        redisUtils.deleteKey("test");
        return ResultUtils.success(true);
    }


    @GetMapping("/redis/has")
    public BaseResponse<Boolean> redisHas() {
        return ResultUtils.success(redisUtils.hasKey("test"));
    }

}
