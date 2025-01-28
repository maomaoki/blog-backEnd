package com.ym.blogBackEnd.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ym.blogBackEnd.common.request.DeleteRequest;
import com.ym.blogBackEnd.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ym.blogBackEnd.model.dto.user.UserSendEmailCode;
import com.ym.blogBackEnd.model.dto.user.UserRegisterDto;
import com.ym.blogBackEnd.model.dto.user.UserUpdateDto;
import com.ym.blogBackEnd.model.dto.user.admin.AdminUserAddDto;
import com.ym.blogBackEnd.model.dto.user.admin.AdminUserQueryDto;
import com.ym.blogBackEnd.model.dto.user.admin.AdminUserUpdateDto;
import com.ym.blogBackEnd.model.vo.user.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author YunMao
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-01-12 23:12:44
 */
public interface UserService extends IService<User> {


    /**
     * 用户 发送 邮箱验证码
     *
     * @param userSendEmailCode
     */
    void userSendEmailCode(UserSendEmailCode userSendEmailCode);


    /**
     * 用户登录
     *
     * @param account
     * @param password
     * @return
     */
    UserVo userLogin(String account, String password, HttpServletRequest request);




    /**
     * 用户 邮箱 注册账号
     *
     * @return 新用户 id
     */
    long userRegister(UserRegisterDto userRegisterDto);


    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginInfo(HttpServletRequest request);


    /**
     * 将 user 脱敏
     *
     * @return
     */
    UserVo userToVo(User user);


    /**
     * 获取 脱敏后的用户 列表
     *
     * @param userList
     * @return
     */
    List<UserVo> userToVoList(List<User> userList);


    /**
     * 根据 登录 更新用户信息
     *
     * @param userUpdateDto
     * @param request
     */
    void userUpdateInfo(UserUpdateDto userUpdateDto, HttpServletRequest request);


    /**
     * 管理员添加用户
     *
     * @param adminUserAddDto
     * @return
     */
    Long adminAddUser(AdminUserAddDto adminUserAddDto);


    /**
     * 管理员编辑用户
     *
     * @param adminUserUpdateDto
     */
    void adminUpdateUser(AdminUserUpdateDto adminUserUpdateDto);


    /**
     * 管理员根据 id 获取用户
     *
     * @param id
     * @return
     */
    User adminGetUserById(Long id);


    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);


    /**
     * 管理员根据 id 获取用户Vo
     *
     * @param id
     * @return
     */
    UserVo adminGetUserVoById(Long id);


    /**
     * 管理员删除用户
     *
     * @param deleteRequest
     * @return
     */
    Boolean adminDeleteUser(DeleteRequest deleteRequest);


    /**
     * 管理员分页查询用户
     *
     * @param adminUserQueryDto
     * @return
     */
    Page<UserVo> adminUserVoByPage(AdminUserQueryDto adminUserQueryDto);


    /**
     * 密码加密加盐
     *
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);


    User userIsLogin(HttpServletRequest request);

    /**
     * 将 user 转成 查询 wrapper
     *
     * @param adminUserQueryDto
     * @return
     */
    QueryWrapper<User> getQueryWrapper(AdminUserQueryDto adminUserQueryDto);


}

