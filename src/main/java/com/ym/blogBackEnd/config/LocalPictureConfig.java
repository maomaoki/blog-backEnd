package com.ym.blogBackEnd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title: localPictureConfig
 * @Author YunMao
 * @Package com.ym.blogBackEnd.manager.picture
 * @Date 2025/1/28 23:18
 * @description: 本地图片配置
 */
@ConfigurationProperties(prefix = "ym.local-picture")
@Component
@Data
public class LocalPictureConfig {

    /**
     * 最大文件大小 (单位 mb /默认 1)
     */
    private int maxSize = 1;

    /**
     * 允许上传的文件类型
     */
    private List<String> allowTypes;

    /**
     * 本地图片存放目录
     */
    private String path;

    /**
     * 本地头像存放目录
     */
    private String avatarPath;

    /**
     * 本地 文章 图片 存放目录
     */
    private String articlePath;

    /**
     * 本地 其他 图片 存放目录
     */
    private String otherPath;


    /**
     * 本地 图片 访问 路径 前缀
     */
    private String url;


}
