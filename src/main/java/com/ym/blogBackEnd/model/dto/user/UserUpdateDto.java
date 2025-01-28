package com.ym.blogBackEnd.model.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
     * 用户标签 - json字符串
     */
    private String userTags;

    /**
     * 用户简介
     */
    private String userIntroduction;

    /**
     * 用户性别：0-男，1-女
     */
    private Integer userFGender;

    /**
     * 用户年龄
     */
    private Integer userAge;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
