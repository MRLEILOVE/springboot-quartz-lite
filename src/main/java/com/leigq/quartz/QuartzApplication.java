package com.leigq.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * The type Quartz application.
 *
 * @author leigq
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@MapperScan("com.leigq.quartz.domain.mapper")
public class QuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }

}
