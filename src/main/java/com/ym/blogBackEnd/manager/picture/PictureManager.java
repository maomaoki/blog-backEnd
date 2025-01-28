package com.ym.blogBackEnd.manager.picture;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Title: PictureManager
 * @Author YunMao
 * @Package com.ym.blogBackEnd.manager.picture
 * @Date 2025/1/28 23:02
 * @description: 图片总体操作方法
 */
public interface PictureManager {


    /**
     * 上传 图片
     *
     * @param picture  图片文件
     * @param key      存放 目录
     * @param fileName 文件名
     * @return 存放路径
     */
    String uploadPicture(File picture, String key, String fileName);


    /**
     * 删除 图片
     *
     * @param picturePath 图片存放 地址
     * @return 是否删除成功
     */
    boolean deletePicture(String picturePath);


}
