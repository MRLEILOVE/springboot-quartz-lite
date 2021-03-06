package com.leigq.quartz.controller;

import com.leigq.quartz.bean.common.Response;
import com.leigq.quartz.bean.constant.SysUserConstant;
import com.leigq.quartz.bean.vo.SysUserVO;
import com.leigq.quartz.util.ImageCode;
import com.leigq.quartz.util.RSACoder;
import com.leigq.quartz.web.exception.ServiceException;
import com.leigq.quartz.web.properties.QuartzProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
     * Instantiates a new Login controller.
     *
     * @param quartzProperties the quartz properties
     */
    public LoginController(QuartzProperties quartzProperties) {
        this.quartzProperties = quartzProperties;
    }

    /**
     * 登录
     *
     * @return the response
     */
    @PostMapping("/login")
    public Response login(@Valid SysUserVO sysUserVO, HttpServletRequest request) {
        String username = decrypt(sysUserVO.getUsername());
        String password = decrypt(sysUserVO.getPassword());
        String timestamp = decrypt(sysUserVO.getTimestamp());
        String validCode = decrypt(sysUserVO.getValidCode());
        String signKey = decrypt(sysUserVO.getSignKey());
        String sign = sysUserVO.getSign();

        // 验证签名
        String splicingSign = DigestUtils.md5Hex(String.format("username=%s&password=%s&timestamp=%s&validCode=%s&signKey=%s",
                username, password, timestamp, validCode, signKey));

        if (!Objects.equals(sign, splicingSign)) {
            return Response.fail("签名验证失败");
        }

        // 验证时间，5秒中之内
        if (System.currentTimeMillis() - Long.parseLong(timestamp) > (5 * 1000)) {
            return Response.fail("登录超时，请重试");
        }

        HttpSession session = request.getSession();
        // 验证验证码
        final Object sessionValidCode = session.getAttribute(SysUserConstant.USER_IMG_VALID_CODE_KEY);
        if (!Objects.equals(sessionValidCode, validCode)) {
            return Response.fail("验证码错误");
        }

        boolean usernameIsTrue = Objects.equals(username, quartzProperties.getTaskView().getLoginUsername());
        boolean passwordIsTrue = Objects.equals(password, quartzProperties.getTaskView().getLoginPassword());
        if (!usernameIsTrue || !passwordIsTrue) {
            return Response.fail("用户名或密码错误，请重试");
        }
        // 将用户保存至 session 中
        session.setAttribute(SysUserConstant.USER_SESSION_KEY, username);
        // 设置30分钟有效期
        session.setMaxInactiveInterval(30 * 60);
        // 删除 session 中保存的图形验证码
        session.removeAttribute(SysUserConstant.USER_IMG_VALID_CODE_KEY);
        return Response.success("登录成功");
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
        return Response.success("退出成功");
    }


    /**
     * 获取图形验证码
     *
     * @param request  the request
     * @param response the response
     */
    @GetMapping("/imgCode")
    public void getImgCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = new ImageCode();
        // 验证码图片
        final BufferedImage image = imageCode.getImage();

        // 验证码字符
        final String validCodeText = imageCode.getValidCodeText();

        // 将验证码保存到Session中。
        HttpSession session = request.getSession();
        session.setAttribute(SysUserConstant.USER_IMG_VALID_CODE_KEY, validCodeText);

        // 禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中
        try (ServletOutputStream sos = response.getOutputStream()){
            ImageIO.write(image, "jpeg", sos);
        }
    }


    private String decrypt(String data) {
        try {
            return RSACoder.decryptByPriKey(data);
        } catch (NoSuchPaddingException | BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | UnsupportedEncodingException | InvalidKeyException e) {
            log.error("RSA解密异常：", e);
            throw new ServiceException("RSA解密失败");
        }
    }

}


