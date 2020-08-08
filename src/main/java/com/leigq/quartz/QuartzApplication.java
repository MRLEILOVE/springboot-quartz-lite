package com.leigq.quartz;

import com.leigq.quartz.web.properties.QuartzProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * The type Quartz application.
 *
 * @author leigq
 */
@SpringBootApplication
@MapperScan("com.leigq.quartz.domain.mapper")
@EnableConfigurationProperties(value = {QuartzProperties.class})
public class QuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }

}
