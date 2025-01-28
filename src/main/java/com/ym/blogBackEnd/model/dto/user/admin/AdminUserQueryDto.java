package com.ym.blogBackEnd.model.dto.user.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ym.blogBackEnd.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: AdminUserQueryDto
 * @Author YunMao
 * @Package com.ym.blogbackend.model.dto.user.admin
 * @Date 2025/1/13 18:03
 * @description: 管理员 分页 查询 用户 请求 类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUserQueryDto extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;


    /**
     *  用户 账号
     */
    private String userAccount;


    /**
     * 用户简介
     */
    private String userIntroduction;

    /**
     * 用户标签 json 字符串
     */
    private String userTags;

    /**
     * 用户角色：boss/admin/user/blackUser
     */
    private String userRole;


    /**
     * 注册来源: 账号/qq邮箱/管理员添加
     */
    private String userRegistrationSource;

    /**
     * 开始 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     *  结束 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
