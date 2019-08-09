## 主要框架

![springboot-version](https://img.shields.io/badge/SpringBoot-2.0.4.RELEASE-orange)
![Quartz-version](https://img.shields.io/badge/Quartz-2.3.0-blue)
![Druid-version](https://img.shields.io/badge/Druid-1.0.29-green)
![mybatis-version](https://img.shields.io/badge/mybatis-1.3.1-lightgrey?link=http://left&link=http://right)

## 效果

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190731095608529.gif)

## 项目结构

下面是整个项目结构，主要类已做注释。

```
├─java
│  └─com
│      └─leigq
│          └─quartz
│              │  QuartzApplication.java
│              │
│              ├─bean
│              │  ├─common
│              │  │      Response.java  -- 统一返回结果
│              │  │
│              │  └─job
│              │          BaseJob.java  -- Job基础接口，其他Job实现此接口
│              │          DetailJobDTO.java
│              │          SimpleJobDTO.java
│              │
│              ├─config
│              │  ├─quartz
│              │  │      QuartzScheduledConfig.java  -- Quartz任务配置
│              │  │
│              │  └─web
│              │          WebMvcConfig.java
│              │
│              ├─controller
│              │      JobController.java
│              │
│              ├─domain
│              │  ├─entity
│              │  │      JobAndTrigger.java
│              │  │
│              │  └─mapper
│              │          JobAndTriggerMapper.java
│              │          JobMapper.java
│              │
│              ├─job
│              │  │  HelloQuartz.java   -- 测试任务，实现BaseJob接口
│              │  │  HelloQuartz1.java  -- 测试任务，实现BaseJob接口 
│              │  │
│              │  └─simple
│              │          SchedulerTest.java  -- 最简单的Quartz
│              │
│              ├─service
│              │      JobAndTriggerService.java
│              │      JobService.java
│              │
│              └─web
│                      GlobalExceptionHand.java  -- 全局异常处理
│
└─resources
    │  application-dev.yml
    │  application-prod.yml
    │  application-test.yml
    │  application.yml
    │
    ├─config  -- 此项目为了在测试、生产环境使用 log4j2 + Mongodb 记录日志，故加入 Mongodb、log4j2 依赖，如不需要，请忽略
    │      log4j2-dev.xml
    │      log4j2-prod.xml
    │      log4j2-test.xml
    │
    ├─help
    │      Hibernate Validator常用注解.md
    │
    ├─mappers
    │      JobAndTriggerMapper.xml
    │      JobMapper.xml
    │
    ├─sql
    │      Quartz官方建表.sql
    │
    └─templates
            job-manager.html
```

结构很简单就不多说了。

建议直接把源码克隆下来运行，源码里面注释很清晰，然后结合下面的几篇文章看，就可以很快理解了。

## 入门

- [Quartz 入门详解](http://www.importnew.com/22890.html)
- [Quartz使用总结](https://www.cnblogs.com/drift-ice/p/3817269.html)

## 参考

- [Spring Boot集成持久化Quartz定时任务管理和界面展示](https://www.cnblogs.com/dekevin/p/8716596.html)

## 源码

 - https://github.com/MRLEILOVE/springboot-quartz.git
