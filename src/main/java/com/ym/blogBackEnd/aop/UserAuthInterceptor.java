package com.ym.blogBackEnd.aop;

import com.ym.blogBackEnd.anntation.UserAuthCheck;
import com.ym.blogBackEnd.enums.UserRoleEnum;
import com.ym.blogBackEnd.exception.CustomizeException;
import com.ym.blogBackEnd.exception.ErrorCode;
import com.ym.blogBackEnd.model.domain.User;
import com.ym.blogBackEnd.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Title: UserAuthInterceptor
 * @Author YunMao
 * @Package com.ym.blogbackend.aop
 * @Date 2025/1/14 17:51
 * @description: 用户权限校验切面
 */
@Aspect
@Component
public class UserAuthInterceptor {

    @Resource
    private UserService userService;


    /**
     * 执行 拦截校验
     *
     * @param point         切入点
     * @param userAuthCheck 注解校验
     * @return
     * @throws Throwable
     */
    @Around("@annotation(userAuthCheck)")
    public Object doIntercept(ProceedingJoinPoint point, UserAuthCheck userAuthCheck) throws Throwable {

        // 1. 拿到注解
        String mustRole = userAuthCheck.mustRole();

        // 2. 排除不需要校验
        if (mustRole == null) {
            // 不需要 校验
            return point.proceed();
        }


        // 3. 拿到登录角色-权限
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User loginInfo = userService.getLoginInfo(request);
        String userRole = loginInfo.getUserRole();

        // 4. 校验权限是否合法
        // 4-1. 校验角色是否 拥有规定的权限
        UserRoleEnum userRoleEnum = UserRoleEnum.getByValue(userRole);
        if (userRoleEnum == null) {
            // 说明 不知权限
            throw new CustomizeException(ErrorCode.ERROR_NO_AUTH_ERROR, "未知权限");
        }

        // 4-2. 校验是否拥有规定的权限
        UserRoleEnum mustRoleEnum = UserRoleEnum.getByValue(mustRole);

        // 注解如果是管理员 角色也必须是管理员权限
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum)
                && !UserRoleEnum.ADMIN.equals(userRoleEnum) &&
                !UserRoleEnum.BOSS.equals(userRoleEnum)
        ) {
            throw new CustomizeException(ErrorCode.ERROR_NO_AUTH_ERROR, "没有权限");
        }

        // 5. 放行
        return point.proceed();
    }

}
