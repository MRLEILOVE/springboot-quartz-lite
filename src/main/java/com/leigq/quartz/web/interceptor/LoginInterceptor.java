package com.leigq.quartz.web.interceptor;

import com.leigq.quartz.bean.constant.SysUserConstant;
import com.leigq.quartz.web.exception.LoginTimeOutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * 登录拦截器
 * <br/>
 * 参考:<a href='http://note.youdao.com/noteshare?id=5ca2c10916c3286df034b78f938b201e'>SpringMVC Interceptor</a>
 * 创建人：leigq <br>
 * 创建时间：2018-11-05 11:26 <br>
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取 session
        HttpSession session = request.getSession();
        Object userKey = session.getAttribute(SysUserConstant.USER_SESSION_KEY);
        if (Objects.isNull(userKey)) {
            // 前后端分离的做法，应该是返回一个执行错误码给前端，让前端去做跳转
            throw new LoginTimeOutException("登录超时，请重新登录");
        }
        return true;
    }
}
