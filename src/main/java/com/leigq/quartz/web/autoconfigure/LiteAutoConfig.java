package com.leigq.quartz.web.autoconfigure;

import com.leigq.quartz.util.RSACoder;
import com.leigq.quartz.web.properties.LiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 本项目自定义属性自动装载
 *
 * @author leiguoqing
 * @date 2020-08-09 21:23:12
 */
@EnableConfigurationProperties(LiteProperties.class)
@Configuration
public class LiteAutoConfig {

    private final Logger log = LoggerFactory.getLogger(LiteAutoConfig.class);

    public LiteAutoConfig(LiteProperties liteProperties) {
        // 初始化 RSACoder 中的公钥、私钥
        RSACoder.PUB_KEY_BASE64 = liteProperties.getSecurity().getAuth().getPubKey();
        try {
            RSACoder.PUB_KEY = RSACoder.restorePubKey(RSACoder.PUB_KEY_BASE64);
            log.info("初始化公钥成功");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("初始化公钥异常：", e);
        }

        RSACoder.PRI_KEY_BASE64 = liteProperties.getSecurity().getAuth().getPriKey();
        try {
            RSACoder.PRI_KEY = RSACoder.restorePriKey(RSACoder.PRI_KEY_BASE64);
            log.info("初始化私钥成功");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("初始化私钥异常：", e);
        }
    }
}
