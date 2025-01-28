package com.ym.blogBackEnd.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ym.blogBackEnd.anntation.UserAuthCheck;
import com.ym.blogBackEnd.common.request.DeleteRequest;
import com.ym.blogBackEnd.common.response.BaseResponse;
import com.ym.blogBackEnd.constant.UserConstant;
import com.ym.blogBackEnd.model.domain.User;
import com.ym.blogBackEnd.model.dto.user.*;
import com.ym.blogBackEnd.model.dto.user.admin.AdminUserAddDto;
import com.ym.blogBackEnd.model.dto.user.admin.AdminUserQueryDto;
import com.ym.blogBackEnd.model.dto.user.admin.AdminUserUpdateDto;
import com.ym.blogBackEnd.model.vo.user.UserVo;
import com.ym.blogBackEnd.service.UserService;
import com.ym.blogBackEnd.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Title: UserController
 * @Author YunMao
 * @Package com.ym.blogbackend.controller
 * @Date 2025/1/13 19:10
 * @description: 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/sendEmailCode")
    public BaseResponse<Boolean> userSendEmailCode(@RequestBody UserSendEmailCode userSendEmailCode) {
        userService.userSendEmailCode(userSendEmailCode);
        return ResultUtils.success(true, "发送成功");

    }


    @PostMapping("/login")
    public BaseResponse<UserVo> userLogin(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        String account = userLoginDto.getAccount();
        String password = userLoginDto.getPassword();
        return ResultUtils.success(userService.userLogin(account, password, request), "登录成功");

    }


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterDto userRegisterDto) {

        return ResultUtils.success(userService.userRegister(userRegisterDto), "注册成功");
    }


    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        return ResultUtils.success(userService.userLogout(request), "退出成功");
    }


    @GetMapping("/get/login")
    public BaseResponse<UserVo> getLoginUser(HttpServletRequest request) {

        User loginInfo = userService.getLoginInfo(request);
        return ResultUtils.success(userService.userToVo(loginInfo), "获取成功");

    }


    @PostMapping("/update")
    public BaseResponse<String> userUpdate(@RequestBody UserUpdateDto userUpdateDto, HttpServletRequest request) {
        userService.userUpdateInfo(userUpdateDto, request);
        return ResultUtils.success("更新成功");
    }


    @PostMapping("/admin/add")
    @UserAuthCheck(mustRole = UserConstant.USER_ROLE_ADMIN)
    public BaseResponse<Long> adminAddUser(@RequestBody AdminUserAddDto adminUserAddDto) {
        Long userId = userService.adminAddUser(adminUserAddDto);
        return ResultUtils.success(userId, "添加成功");
    }


    @PostMapping("/admin/update")
    @UserAuthCheck(mustRole = UserConstant.USER_ROLE_ADMIN)
    public BaseResponse<Boolean> adminUpdateUser(@RequestBody AdminUserUpdateDto adminUserUpdateDto) {
        userService.adminUpdateUser(adminUserUpdateDto);
        return ResultUtils.success(true, "修改成功");
    }


    @PostMapping("/admin/delete")
    @UserAuthCheck(mustRole = UserConstant.USER_ROLE_ADMIN)
    public BaseResponse<Boolean> adminDeleteUser(@RequestBody DeleteRequest deleteRequest) {
        return ResultUtils.success(userService.adminDeleteUser(deleteRequest), "删除成功");
    }

    @PostMapping("/admin/get/user")
    @UserAuthCheck(mustRole = UserConstant.USER_ROLE_ADMIN)
    public BaseResponse<User> adminGetUserById(@RequestBody DeleteRequest deleteRequest) {
        User user = userService.adminGetUserById(deleteRequest.getId());
        return ResultUtils.success(user, "查询成功");
    }


    @PostMapping("/admin/get/userVo")
    @UserAuthCheck(mustRole = UserConstant.USER_ROLE_ADMIN)
    public BaseResponse<UserVo> adminGetUserVoById(@RequestBody DeleteRequest deleteRequest) {
        UserVo userVo = userService.adminGetUserVoById(deleteRequest.getId());
        return ResultUtils.success(userVo, "查询成功");
    }


    @PostMapping("/admin/list/page")
    @UserAuthCheck(mustRole = UserConstant.USER_ROLE_ADMIN)
    public BaseResponse<Page<UserVo>> AdminUserQuery(@RequestBody AdminUserQueryDto adminUserQueryDto) {
        Page<UserVo> userVoPage = userService.adminUserVoByPage(adminUserQueryDto);
        return ResultUtils.success(userVoPage, "查询成功");
    }
}
