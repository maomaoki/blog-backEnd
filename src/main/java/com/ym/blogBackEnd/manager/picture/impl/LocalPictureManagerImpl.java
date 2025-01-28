package com.ym.blogBackEnd.manager.picture.impl;

import com.ym.blogBackEnd.manager.picture.PictureManager;
import com.ym.blogBackEnd.config.LocalPictureConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @Title: PictureManagerImpl
 * @Author YunMao
 * @Package com.ym.blogBackEnd.manager.picture.impl
 * @Date 2025/1/28 23:05
 * @description: 本地操作图片方法实现类
 */
@Slf4j
@Component
public class LocalPictureManagerImpl implements PictureManager {

    @Resource
    private LocalPictureConfig localPictureConfig;


    /**
     * 上传 图片
     *
     * @param picture  图片文件
     * @param key      存放 目录
     * @param fileName 文件名
     * @return 存放路径
     */
    @Override
    public String uploadPicture(File picture, String key, String fileName) {

        // 创建上传目录
        File uploadDir = new File(key);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 创建上传文件
        File uploadFile = new File(uploadDir, fileName);
        // 上传文件
        try {
            // 这里是将file 写入 存放文件
            Files.copy(picture.toPath(), uploadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return key + fileName;
        } catch (Exception e) {
            log.error("本地存储文件错误:" + e);
        } finally {
            picture.delete();
        }
        return null;
    }

    /**
     * 删除 图片
     *
     * @param picturePath 图片存放 地址
     * @return 是否删除成功
     */
    @Override
    public boolean deletePicture(String picturePath) {
        return false;
    }
}
