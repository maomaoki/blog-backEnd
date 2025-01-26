package com.ym.blogBackEnd.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title: UserUpdateDto
 * @Author YunMao
 * @Package com.ym.blogbackend.model.dto.user
 * @Date 2025/1/13 17:52
 * @description: 用户 更新 请求 类
 */
@Data
public class UserUpdateDto implements Serializable {


    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户标签 json 字符串
     */
    private String userTags;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
