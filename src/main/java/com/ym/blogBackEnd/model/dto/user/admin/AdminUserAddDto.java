package com.ym.blogBackEnd.model.dto.user.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title: AdminUserAddDto
 * @Author YunMao
 * @Package com.ym.blogbackend.model.dto.user.admin
 * @Date 2025/1/13 17:59
 * @description: 管理员 添加用户 请求 类
 */
@Data
public class AdminUserAddDto implements Serializable {

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;


    /**
     * 用户角色：boss/admin/user/blackUser
     */
    private String userRole;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
