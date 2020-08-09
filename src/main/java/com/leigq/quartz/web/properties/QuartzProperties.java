package com.leigq.quartz.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description
 *
 * @author leiguoqing
 * @date 2020-08-08 19:27:40
 */
@ConfigurationProperties("spring.quartz")
public class QuartzProperties {

    private TaskView taskView;

    public TaskView getTaskView() {
        return taskView;
    }

    public void setTaskView(TaskView taskView) {
        this.taskView = taskView;
    }

    public static class TaskView {
        /**
         * 登录用户名
         */
        private String loginUsername = "admin";

        /**
         * 登录密码
         */
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


}
