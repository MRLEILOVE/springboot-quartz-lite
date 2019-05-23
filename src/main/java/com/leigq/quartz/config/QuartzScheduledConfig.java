package com.leigq.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

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
@Configuration
@Slf4j
public class QuartzScheduledConfig {

    @Autowired
    private SpringJobFactory springJobFactory;

    /**
     * 读取quartz.properties 文件
     * 将值初始化
     *
     * @return
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        final Properties properties = propertiesFactoryBean.getObject();
        log.warn("读取quartz.properties 文件:{}", properties);
        return properties;
    }

    /**
     * 将配置文件的数据加载到SchedulerFactoryBean中
     *
     * @return
     * @throws IOException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());
        factory.setAutoStartup(true);
        // 延时n秒启动
        factory.setStartupDelay(2);
        // 注意这里是重点
        factory.setJobFactory(springJobFactory);
        return factory;
    }

    /**
     * 初始化监听器
     *
     * @return
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    /**
     * 获得Scheduler 对象
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

}
