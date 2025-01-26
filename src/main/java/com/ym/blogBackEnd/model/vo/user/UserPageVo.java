package com.ym.blogBackEnd.model.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: UserPageVo
 * @Author YunMao
 * @Package com.ym.blogbackend.model.vo.user
 * @Date 2025/1/16 17:55
 * @description: 管理员分页查询用户返回vo
 */
@Data
public class UserPageVo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 手机号
     */
    private String userPhone;


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

    /**
     * 注册来源: 账号/qq邮箱/管理员添加
     */
    private String userRegistrationSource;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
