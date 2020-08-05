package com.leigq.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@MapperScan("com.leigq.quartz.domain.mapper")
public class QuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuartzApplication.class, args);
	}

}
