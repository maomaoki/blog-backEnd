package com.ym.blogBackEnd.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Title: RedisUtils
 * @Author YunMao
 * @Package com.ym.blogbackend.utils
 * @Date 2025/1/14 11:50
 * @description: redis操作工具类
 */
@Component
public class RedisUtils {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 设置 key-value 并且 设置 过期时间
     *
     * @param key   key
     * @param value value
     * @param time  过期时间 (s)
     */
    public void setValue(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);

    }


    /**
     * 获取 value
     *
     * @param key key
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除 key
     *
     * @param key key
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 判断 key 是否存在
     *
     * @param key key
     * @return true 存在 false 不存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

}
