package com.leigq.quartz.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description
 *
 * @author leiguoqing
 * @date 2020-08-08 19:27:40
 */
@ConfigurationProperties("spring.quartz.task-view")
public class QuartzProperties {

    private String loginUsername = "admin";

    private String loginPassword = "admin";


    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
