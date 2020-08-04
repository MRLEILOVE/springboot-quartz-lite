package com.leigq.quartz.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz任务配置
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019-03-05 15:37 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Slf4j
@Configuration
public class QuartzConfig implements SchedulerFactoryBeanCustomizer {

    /**
     * 自定义配置
     *
     * @param schedulerFactoryBean the scheduler factory bean
     */
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        // 设置自动启动
        schedulerFactoryBean.setAutoStartup(true);
        // 延时n秒启动
        schedulerFactoryBean.setStartupDelay(2);
    }
}
