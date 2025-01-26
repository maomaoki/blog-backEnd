package com.ym.blogBackEnd.model.vo.user;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @Title: UserVo
 * @Author YunMao
 * @Package com.ym.blogbackend.model.vo.user
 * @Date 2025/1/13 18:07
 * @description: 用户脱敏响应类
 */
@Data
public class UserVo implements Serializable {
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

    // todo 少了一个登录ip


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
