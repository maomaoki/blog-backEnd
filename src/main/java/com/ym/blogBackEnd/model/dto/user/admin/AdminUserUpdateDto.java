package com.ym.blogBackEnd.model.dto.user.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title: AdminUserUpdateDto
 * @Author YunMao
 * @Package com.ym.blogbackend.model.dto.user.admin
 * @Date 2025/1/13 17:59
 * @description: 管理员 更新 用户 请求 类
 */
@Data
public class AdminUserUpdateDto implements Serializable {

    /**
     * id
     */
    private Long id;


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

    /**
     * 用户角色：boss/admin/user/blackUser
     */
    private String userRole;

    /**
     * 是否禁用 0 正常 1 禁用
     */
    private Integer userStatus;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
