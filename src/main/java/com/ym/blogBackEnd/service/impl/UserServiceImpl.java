package com.ym.blogBackEnd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.blogBackEnd.common.entity.EmailCodeRedis;
import com.ym.blogBackEnd.common.request.DeleteRequest;
import com.ym.blogBackEnd.config.UserConfig;
import com.ym.blogBackEnd.constant.CodeTypeConstant;
import com.ym.blogBackEnd.constant.UserConstant;
import com.ym.blogBackEnd.enums.EmailCodeTypeEnum;
import com.ym.blogBackEnd.exception.CustomizeException;
import com.ym.blogBackEnd.exception.ErrorCode;
import com.ym.blogBackEnd.model.domain.User;
import com.ym.blogBackEnd.model.dto.user.UserSendEmailCode;
import com.ym.blogBackEnd.model.dto.user.UserRegisterDto;
import com.ym.blogBackEnd.model.dto.user.UserUpdateDto;
import com.ym.blogBackEnd.model.dto.user.admin.AdminUserAddDto;
import com.ym.blogBackEnd.model.dto.user.admin.AdminUserQueryDto;
import com.ym.blogBackEnd.model.dto.user.admin.AdminUserUpdateDto;
import com.ym.blogBackEnd.model.vo.user.UserVo;
import com.ym.blogBackEnd.service.UserService;
import com.ym.blogBackEnd.mapper.UserMapper;
import com.ym.blogBackEnd.utils.EmailUtils;
import com.ym.blogBackEnd.utils.RedisUtils;
import com.ym.blogBackEnd.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 54621
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2025-01-12 23:12:44
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Resource
    private UserConfig userConfig;

    @Resource
    private EmailUtils emailUtils;
    @Autowired
    private RedisUtils redisUtils;


    /**
     * 用户 发送 邮箱验证码
     *
     * @param userSendEmailCode
     */
    @Override
    public void userSendEmailCode(UserSendEmailCode userSendEmailCode) {

        String account = userSendEmailCode.getAccount();
        String email = userSendEmailCode.getEmail();
        Integer type = userSendEmailCode.getType();

        // 1. 校验参数
        ThrowUtils.throwIf(
                ObjUtil.hasEmpty(
                        account,
                        email,
                        type
                ),
                ErrorCode.ERROR_PARAMS,
                "参数不能为空"
        );

        // 2. 校验 类型 是否 存在
        ThrowUtils.throwIf(
                EmailCodeTypeEnum.getByValue(type) == null,
                ErrorCode.ERROR_PARAMS,
                "验证码类型不存在"
        );


        // 这里需要 拦截不能一直发 同一个
        // 3. 查询 redis 是否存在
        EmailCodeRedis emailCodeRedis = (EmailCodeRedis) redisUtils.getValue(email);
        if (emailCodeRedis != null && (emailCodeRedis.getAccount().equals(account) || emailCodeRedis.getType().equals(type))) {
            throw new CustomizeException(ErrorCode.ERROR_OPERATION_ERROR, "请勿频繁发送验证码");
        }


        // 3. 封装 需要发送验证码数据
        String code = RandomUtil.randomString(6);
        String subject = "验证码";
        String content = "云猫验证码" + code + "请三分钟内添加成功!";


        // 4. 发送
        emailUtils.sendEmail(email, subject, content);


        // 5. 封装 存入 redis 数据
        // 这里 用 邮箱作为key
        // 因为 邮箱限制多一点
        emailCodeRedis = new EmailCodeRedis();
        emailCodeRedis.setAccount(account);
        emailCodeRedis.setEmail(email);
        emailCodeRedis.setCode(code);
        emailCodeRedis.setType(type);

        // 6. 插入redis 并且 设置 过期时间
        // account:emailCodeRedis
        redisUtils.setValue(email, emailCodeRedis, 60 * 3);

    }

    /**
     * 用户登录
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public UserVo userLogin(String account, String password, HttpServletRequest request) {

        // 1. 校验参数
        verifyAccountAndPassword(account, password, password);

        // 2. 密码加密
        String encryptPassword = getEncryptPassword(password);

        // 3. 查询 是否 存在(可以账号或者邮箱登录)
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", account)
                .or()
                .eq("userEmail", account);
        userQueryWrapper.eq("userPassword", encryptPassword);

        User user = this.baseMapper.selectOne(userQueryWrapper);
        if (user == null) {
            log.info("user is not have");
            throw new CustomizeException(
                    ErrorCode.ERROR_PARAMS,
                    "用户不存在或者密码错误");

        }

        // 4. 查询 账号状态
        Integer userStatus = user.getUserStatus();
        ThrowUtils.throwIf(
                Objects.equals(userStatus, UserConstant.USER_STATUS_IS_NOT_ACTIVE),
                ErrorCode.ERROR_OPERATION_ERROR,
                "账号已封禁,请联系管理员!"
        );

        // 5. 登录成功 存 session
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);

        // 返回 脱敏的 用户 信息
        return userToVo(user);
    }


    /**
     * 用户注册
     *
     * @param userRegisterDto
     * @return
     */
    @Override
    public long userRegister(UserRegisterDto userRegisterDto) {

        // 1.校验参数(这里 只需要 校验 验证码相关的 剩下服用前面注册代码)
        String account = userRegisterDto.getAccount();
        String password = userRegisterDto.getPassword();
        String confirmPassword = userRegisterDto.getConfirmPassword();
        String email = userRegisterDto.getEmail();
        String code = userRegisterDto.getCode();
        ThrowUtils.throwIf(
                StrUtil.hasBlank(account, email, code),
                ErrorCode.ERROR_PARAMS, "账号或邮箱或验证码不能为空");

        // 2. 查询 验证码 是否过期
        EmailCodeRedis emailCodeRedis = (EmailCodeRedis) redisUtils.getValue(email);
        ThrowUtils.throwIf(
                ObjUtil.isEmpty(emailCodeRedis),
                ErrorCode.ERROR_NOT_FOUND_ERROR, "验证码已过期");

        // 3. 查询 验证码 是否 合法 - 验证码类型是否是注册
        String redisEmail = emailCodeRedis.getEmail();
        String redisAccount = emailCodeRedis.getAccount();
        String redisCode = emailCodeRedis.getCode();
        Integer redisType = emailCodeRedis.getType();

        ThrowUtils.throwIf(
                !redisEmail.equals(email) ||
                        !redisAccount.equals(account) ||
                        !redisCode.equals(code) ||
                        !redisType.equals(CodeTypeConstant.REGISTER_TYPE),
                ErrorCode.ERROR_PARAMS, "验证码错误");

        // 4. 校验 账号 密码 是否合法
        verifyAccountAndPassword(account, password, confirmPassword);

        // 5. 查询 用户 是否 存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", account)
                .or()
                .eq("userEmail", email);
        User user = this.baseMapper.selectOne(userQueryWrapper);
        if (user != null) {
            log.info("user is have, Don't repeat registration");
            throw new CustomizeException(
                    ErrorCode.ERROR_PARAMS,
                    "账号重复"
            );
        }


        // 5. 正常注册
        user = new User();
        // 密码 一定要 加密
        user.setUserPassword(getEncryptPassword(password));
        // 添加 一些 初始值
        user.setUserAccount(account);
        user.setUserEmail(email);
        user.setUserRole(UserConstant.USER_ROLE_USER);
        user.setUserStatus(UserConstant.USER_STATUS_IS_ACTIVE);
        user.setUserName(StrUtil.subPre(account, 5));
        // 注册来源 email
        user.setRegisteredSource(UserConstant.USER_REGISTER_FROM_EMAIL);

        // 5. 插入数据库
        this.baseMapper.insert(user);

        // 6. 删除redis
        redisUtils.deleteKey(account);

        return user.getId();
    }

    /**
     * 用户 注销
     *
     * @param request
     * @return
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {

        // 1. 判断是否登录
        userIsLogin(request);

        // 2. 移除 session
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);

        // 3. 返回
        return true;
    }

    /**
     * 获取 登录 用户 信息
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginInfo(HttpServletRequest request) {
        // 返回 新 的 数据
        return userIsLogin(request);

    }

    /**
     * 根据 登录 用户 更新用户信息
     *
     * @param userUpdateDto
     * @param request
     */
    @Override
    public void userUpdateInfo(UserUpdateDto userUpdateDto, HttpServletRequest request) {

        // 1. 判断是否登录
        User loginUser = userIsLogin(request);
        // 2. 创建 需要 更新 用户 的信息
        User updateUser = new User();
        BeanUtil.copyProperties(userUpdateDto, updateUser);
        // 记住需要 添加用户id(要不然就更新不了)
        updateUser.setId(loginUser.getId());
        // 3. 更新
        this.baseMapper.updateById(updateUser);

    }


    /**
     * 管理员 添加 用户
     *
     * @param adminUserAddDto
     * @return
     */
    @Override
    public Long adminAddUser(AdminUserAddDto adminUserAddDto) {

        // 校验 参数
        ThrowUtils.throwIf(
                ObjUtil.isEmpty(adminUserAddDto) ||
                        ObjUtil.isEmpty(adminUserAddDto.getUserAccount()),
                ErrorCode.ERROR_PARAMS,
                "参数不能为空"
        );


        // 2.是否存在此用户
        String userAccount = adminUserAddDto.getUserAccount();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        User user = this.baseMapper.selectOne(userQueryWrapper);
        if (user != null) {
            log.info("user is have, Don't repeat registration");
            throw new CustomizeException(
                    ErrorCode.ERROR_PARAMS,
                    "账号重复"
            );
        }


        // 正常 注册
        // 1. 转 添加 参数
        user = new User();
        BeanUtil.copyProperties(adminUserAddDto, user);

        // 2. 补 默认参数
        user.setUserStatus(UserConstant.USER_STATUS_IS_ACTIVE);
        user.setRegisteredSource(UserConstant.USER_REGISTER_FROM_ADMIN);
        user.setUserPassword(getEncryptPassword(userConfig.getDefaultPassword()));

        if (StrUtil.isEmpty(user.getUserName())) {
            user.setUserName(StrUtil.subPre(userAccount, 5));
        } else {
            user.setUserName(user.getUserName());
        }

        // 3. 添加 用户
        this.baseMapper.insert(user);
        return user.getId();
    }

    /**
     * 管理员 更新 用户
     *
     * @param adminUserUpdateDto
     */
    @Override
    public void adminUpdateUser(AdminUserUpdateDto adminUserUpdateDto) {

        // 校验 参数
        ThrowUtils.throwIf(
                ObjUtil.isEmpty(adminUserUpdateDto) ||
                        ObjUtil.isEmpty(adminUserUpdateDto.getId())
                ,
                ErrorCode.ERROR_PARAMS,
                "参数不能为空"
        );


        // 1. 需要 分 情况 如果 需要 修改 角色
        String userRole = adminUserUpdateDto.getUserRole();

        // 2. 当 从别的 角色 变成 黑名单 需要 将 账号 状态 禁止
        if (!StrUtil.isEmpty(userRole) && StrUtil.equals(userRole, UserConstant.USER_ROLE_BLACK_USER)) {
            adminUserUpdateDto.setUserStatus(UserConstant.USER_STATUS_IS_NOT_ACTIVE);
        }

        // 3. 当 从 黑名单 变成 其他 角色 需要 将 账号 状态 激活
        if (!StrUtil.isEmpty(userRole) && !StrUtil.equals(userRole, UserConstant.USER_ROLE_BLACK_USER)) {
            adminUserUpdateDto.setUserStatus(UserConstant.USER_STATUS_IS_ACTIVE);
        }

        User user = new User();
        BeanUtil.copyProperties(adminUserUpdateDto, user);
        // 2. 更新
        this.baseMapper.updateById(user);
    }

    /**
     * 管理员根据 id 获取用户
     *
     * @param id
     * @return
     */
    @Override
    public User adminGetUserById(Long id) {

        // 校验 参数
        ThrowUtils.throwIf(
                id < 0,
                ErrorCode.ERROR_PARAMS,
                "请求id错误"
        );
        // 根据 id 查询 用户
        User user = this.baseMapper.selectById(id);
        ThrowUtils.throwIf(
                ObjUtil.isEmpty(user),
                ErrorCode.ERROR_NOT_FOUND_ERROR,
                "用户不存在");

        // 返回 对象
        return user;

    }


    /**
     * 管理员根据 id 获取用户Vo
     *
     * @param id
     * @return
     */
    @Override
    public UserVo adminGetUserVoById(Long id) {

        // 校验 参数
        ThrowUtils.throwIf(
                id < 0,
                ErrorCode.ERROR_PARAMS,
                "请求id错误"
        );
        // 根据 id 查询 用户
        User user = this.baseMapper.selectById(id);
        ThrowUtils.throwIf(
                ObjUtil.isEmpty(user),
                ErrorCode.ERROR_NOT_FOUND_ERROR,
                "用户不存在");

        // 返回 对象
        return userToVo(user);

    }


    /**
     * 管理员删除用户
     *
     * @param deleteRequest
     * @return
     */
    @Override
    public Boolean adminDeleteUser(DeleteRequest deleteRequest) {

        // 校验 参数
        ThrowUtils.throwIf(
                ObjUtil.isEmpty(deleteRequest) ||
                        ObjUtil.isEmpty(deleteRequest.getId()),
                ErrorCode.ERROR_PARAMS, "参数不能为空");

        // 构建 删除 用户 
        Long deleteUserId = deleteRequest.getId();
        // 删除 用户
        this.baseMapper.deleteById(deleteUserId);
        // 返回结果
        return true;
    }


    /**
     * 管理员分页查询用户
     *
     * @param adminUserQueryDto
     * @return
     */
    @Override
    public Page<UserVo> adminUserVoByPage(AdminUserQueryDto adminUserQueryDto) {
        // 校验 参数
        ThrowUtils.throwIf(
                ObjUtil.isEmpty(adminUserQueryDto),
                ErrorCode.ERROR_PARAMS, "参数不能为空");


        // 构建 分页 查询
        int current = adminUserQueryDto.getCurrent();
        int pageSize = adminUserQueryDto.getPageSize();
        QueryWrapper<User> queryWrapper = getQueryWrapper(adminUserQueryDto);

        // 查询
        Page<User> userPage = this.baseMapper.selectPage(
                new Page<>(current, pageSize),
                queryWrapper
        );

        // 结果 脱敏
        List<UserVo> userToVoList = userToVoList(userPage.getRecords());

        // 构建 分页 对象
        Page<UserVo> userPageVoPage = new Page<>(current, pageSize, userPage.getTotal());
        userPageVoPage.setRecords(userToVoList);
        return userPageVoPage;
    }

    /**
     * 密码加密加盐
     *
     * @param userPassword
     * @return
     */
    @Override
    public String getEncryptPassword(String userPassword) {
        return DigestUtils.md5DigestAsHex((userConfig.getEncrypt() + userPassword).getBytes());
    }

    /**
     * 判断 是否 为 管理员(boss)
     *
     * @param user
     * @return
     */
    @Override
    public boolean isAdmin(User user) {

        String userRole = user.getUserRole();

        // 判断是否 为 admin 或者 boss
        return StrUtil.equals(userRole, UserConstant.USER_ROLE_ADMIN) ||
                StrUtil.equals(userRole, UserConstant.USER_ROLE_BOSS);

    }


    /**
     * 用户 是否 登录
     *
     * @param request
     * @return
     */
    @Override
    public User userIsLogin(HttpServletRequest request) {

        // 1. 判断是否登录
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        ThrowUtils.throwIf(
                ObjUtil.isEmpty(user),
                ErrorCode.ERROR_NOT_LOGIN_ERROR,
                "用户未登录"
        );

        // 2. 可能 数据 会发生变化
        Long userId = user.getId();
        user = this.baseMapper.selectById(userId);

        // 3. 如果 用户 被突然 删除了 也是 需要 作出提示
        if (ObjUtil.isEmpty(user)) {
            // 删除 登录态
            request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
            throw new CustomizeException(ErrorCode.ERROR_NOT_LOGIN_ERROR, "用户不存在");
        }

        // 4.  如果 用户 状态 为禁止  需要 修改 登录态
        Integer userStatus = user.getUserStatus();
        if (userStatus.equals(UserConstant.USER_STATUS_IS_NOT_ACTIVE)) {
            // 删除 登录态
            request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
            // 抛出 账号 异常
            throw new CustomizeException(ErrorCode.ERROR_NOT_LOGIN_ERROR, "账号已被封禁");
        }
        // 2.返回 登录对象
        return user;
    }


    /**
     * 将 user 脱敏
     *
     * @return
     */
    @Override
    public UserVo userToVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user, userVo);
        return userVo;
    }


    @Override
    public List<UserVo> userToVoList(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::userToVo).collect(Collectors.toList());
    }


    /**
     * 构建 查询 条件
     *
     * @param adminUserQueryDto
     * @return
     */
    @Override
    public QueryWrapper<User> getQueryWrapper(AdminUserQueryDto adminUserQueryDto) {

        // 校验 参数
        ThrowUtils.throwIf(
                ObjUtil.isEmpty(adminUserQueryDto),
                ErrorCode.ERROR_PARAMS, "参数不能为空");

        // 构建 查询 条件
        Long id = adminUserQueryDto.getId();
        String userAccount = adminUserQueryDto.getUserAccount();
        String userName = adminUserQueryDto.getUserName();
        String userIntroduction = adminUserQueryDto.getUserIntroduction();
        String userTags = adminUserQueryDto.getUserTags();
        String userRole = adminUserQueryDto.getUserRole();
        String userRegistrationSource = adminUserQueryDto.getUserRegistrationSource();
        Date startTime = adminUserQueryDto.getStartTime();
        Date endTime = adminUserQueryDto.getEndTime();
        String sortField = adminUserQueryDto.getSortField();
        String sortOrder = adminUserQueryDto.getSortOrder();


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNotNull(id), "id", id);
        queryWrapper.eq(StrUtil.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.eq(StrUtil.isNotBlank(userRegistrationSource), "userRegistrationSource", userRegistrationSource);
        queryWrapper.like(StrUtil.isNotBlank(userAccount), "userAccount", userAccount);
        queryWrapper.like(StrUtil.isNotBlank(userName), "userName", userName);
        queryWrapper.like(StrUtil.isNotBlank(userIntroduction), "userIntroduction", userIntroduction);

        // 标签
        // 转 list 再 like 拼接
        if (StrUtil.isNotEmpty(userTags)) {
            List<String> tagList = JSONUtil.toList(userTags, String.class);
            for (String tag : tagList) {
                queryWrapper.like("userTags", "\"" + tag + "\"");
            }

        }

        // 时间 创建时间
        // 开始时间
        queryWrapper.ge(
                ObjUtil.isNotNull(startTime), "createTime", startTime
        );
        // 结束时间
        queryWrapper.le(
                ObjUtil.isNotNull(startTime), "createTime", endTime
        );


        queryWrapper.orderBy(StrUtil.isNotEmpty(sortField), sortOrder.equals("ascend"), sortField);

        return queryWrapper;
    }


    /**
     * 校验 账号 和 密码
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     */
    private void verifyAccountAndPassword(String userAccount, String userPassword, String checkPassword) {
        boolean result =
                userAccount.length() < userConfig.getAccountLength() ||
                        userPassword.length() < userConfig.getPasswordLength() ||
                        checkPassword.length() < userConfig.getPasswordLength() ||
                        userPassword.length() != checkPassword.length();

        ThrowUtils.throwIf(
                result,
                ErrorCode.ERROR_PARAMS,
                "账号错误或者密码错误");

        ThrowUtils.throwIf(
                !userPassword.equals(checkPassword),
                ErrorCode.ERROR_PARAMS,
                "两次密码不正确");
    }

}




