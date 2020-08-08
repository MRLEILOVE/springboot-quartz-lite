package com.leigq.quartz.controller;

import com.leigq.quartz.bean.common.Response;
import com.leigq.quartz.bean.constant.SysUserConstant;
import com.leigq.quartz.bean.vo.SysUserVO;
import com.leigq.quartz.web.properties.QuartzProperties;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 登录 Controller
 * <p>
 *
 * @author leigq <br>
 * @date 2020 /08/04 14:23 <br> </p>
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class LoginController {

    /**
     * The Quartz properties.
     */
    private final QuartzProperties quartzProperties;
    /**
     * The Response.
     */
    private final Response response;

    /**
     * Instantiates a new Login controller.
     *
     * @param quartzProperties the quartz properties
     * @param response         the response
     */
    public LoginController(QuartzProperties quartzProperties, Response response) {
        this.quartzProperties = quartzProperties;
        this.response = response;
    }

    /**
     * 登录
     *
     * @return the response
     */
    @PostMapping("/login")
    public Response login(@Valid SysUserVO sysUserVO, HttpServletRequest request) {
        var username = sysUserVO.getUsername();
        if (!Objects.equals(username, quartzProperties.getLoginUsername())) {
            return response.failure("用户名错误");
        }
        var password = sysUserVO.getPassword();
        if (!Objects.equals(password, quartzProperties.getLoginPassword())) {
            return response.failure("密码错误");
        }
        // 将用户保存至 session 中
        HttpSession session = request.getSession();
        session.setAttribute(SysUserConstant.USER_SESSION_KEY, username);
        // 设置30分钟有效期
        session.setMaxInactiveInterval(30 * 60);
        return response.success("登录成功");
    }


    /**
     * 登出
     *
     * @return the response
     */
    @PostMapping("/logout")
    public Response logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userKey = session.getAttribute(SysUserConstant.USER_SESSION_KEY);
        if (Objects.nonNull(userKey)) {
            session.removeAttribute(SysUserConstant.USER_SESSION_KEY);
        }
        return response.success("退出成功");
    }

}
