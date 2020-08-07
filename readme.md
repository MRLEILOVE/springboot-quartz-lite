<center>

[![Fork me on Gitee](https://gitee.com/leiguoqing/springboot-quartz-lite/widgets/widget_4.svg)](https://gitee.com/leiguoqing/springboot-quartz-lite)

<h3>基于Quartz的可视化任务系统</h3>

<a href='https://spring.io/projects/spring-boot'>![springboot-version](https://img.shields.io/badge/SpringBoot-2.0.8.RELEASE-orange)</a>
<a href='http://www.quartz-scheduler.org/'>![Quartz-version](https://img.shields.io/badge/Quartz-2.3.0-blue)</a>
<a href='http://druid.apache.org/'>![druid-spring-boot](https://img.shields.io/badge/Druid-1.1.10-green)</a>
<a href='https://mp.baomidou.com/'>![mybatis-plus-version](https://img.shields.io/badge/MybatisPlus-3.2.0-lightgrey?link=http://left&link=http://right)</a>
<a href='https://www.apache.org/licenses/LICENSE-2.0'>![apache](https://img.shields.io/badge/license-Apache2-red?link=http://left&link=http://right)</a>

</center>


## 功能展示

### 任务列表

任务列表显示每个任务的基本信息，可对任务进行`立即执行`、`暂停`、`恢复`、`删除`、`修改`、`日志查询`操作。

对于不会写 cron 表达式的同学，可以点击左上角的 `在线生成Cron` 按钮进行生成。

右上角可以设置页面的自动刷新频率，默认一秒钟刷新一次。

![](https://leigq-blog.oss-cn-shenzhen.aliyuncs.com/csdn/20200807132359.png)

![](https://leigq-blog.oss-cn-shenzhen.aliyuncs.com/csdn/20200807132914.png)

![](https://leigq-blog.oss-cn-shenzhen.aliyuncs.com/csdn/20200807132853.png)

![](https://leigq-blog.oss-cn-shenzhen.aliyuncs.com/csdn/20200807132936.png)

### 任务日志

![](https://leigq-blog.oss-cn-shenzhen.aliyuncs.com/csdn/20200807133044.png)

![](https://leigq-blog.oss-cn-shenzhen.aliyuncs.com/csdn/20200807133030.png)

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
│              │  ├─constant
│              │  │      QuartzTriggerConstant.java
│              │  │
│              │  ├─dto
│              │  │      AddQuartzJobDTO.java
│              │  │      TaskExecuteDTO.java
│              │  │
│              │  ├─enumeration
│              │  │      SysTaskExecResultEnum.java
│              │  │
│              │  ├─job
│              │  │      BaseJob.java
│              │  │      BaseJobDisallowConcurrent.java
│              │  │      BaseTaskExecute.java  -- 任务基础抽象类，其他任务继承此类，实现其execute方法执行任务
│              │  │
│              │  └─vo
│              │          AddSysTaskVO.java
│              │          SysTaskListVO.java
│              │          SysTaskLogListVO.java
│              │          UpdateSysTaskVO.java
│              │
│              ├─controller
│              │      SysTaskController.java
│              │      SysTaskLogController.java
│              │
│              ├─domain
│              │  ├─entity
│              │  │      SysTask.java
│              │  │      SysTaskLog.java
│              │  │
│              │  └─mapper
│              │          SysTaskLogMapper.java
│              │          SysTaskMapper.java
│              │
│              ├─service
│              │      QuartzJobService.java
│              │      SysTaskLogService.java
│              │      SysTaskService.java
│              │
│              ├─task
│              │      HelloQuartz.java
│              │
│              ├─util
│              │      SpringContextHolder.java
│              │      ValidUtils.java
│              │
│              └─web
│                  ├─config
│                  │      MvcConfig.java
│                  │      MyBatisPlusConfig.java
│                  │      OrikaConfig.java
│                  │      QuartzConfig.java  -- Quartz任务配置
│                  │
│                  └─exception
│                          GlobalExceptionHand.java  -- 全局异常处理
│                          ServiceException.java
│
└─resources
    ├─config
    │      application.yml
    │      log4j2-config.xml
    │
    ├─mapper
    │      SysTaskLogMapper.xml
    │      SysTaskMapper.xml
    │
    ├─sql
    │      Quartz官方建表.sql
    │      自定义任务和任务日志表.sql
    │
    └─templates
            task-log.html      -- 任务列表页面
            task-manager.html  -- 任务日志列表页面
```

结构很简单就不多说了。

建议直接把源码克隆下来运行，源码里面注释很清晰，然后结合下面的几篇文章看，就可以很快理解了。

## 运行项目

1. 新建一个数据库，执行项目中 resource/sql 下面的SQL语句，先执行 `Quartz官方建表.sql`，再执行 `自定义任务和任务日志表.sql`。
2. 将项目中 `application.yml` 中的 `datasource.password` 、 `datasource.url`、`datasource.username` 改为对应你自己的。
3. 运行项目，项目启动后，在浏览器输入：<http://localhost:8080/task-manager.html> 即可进入任务管理页面。
4. 此时是一个任务都没有的，项目的 src\main\java\com\leigq\quartz\task 目录下有一个任务示例，你可以把他添加进去看看效果。

![](https://leigq-blog.oss-cn-shenzhen.aliyuncs.com/csdn/20200807145627.png)

![](https://leigq-blog.oss-cn-shenzhen.aliyuncs.com/csdn/20200807145658.png)

## 其它资料

### 入门教程

- [Quartz 入门详解](http://www.importnew.com/22890.html)
- [Quartz使用总结](https://www.cnblogs.com/drift-ice/p/3817269.html)

### 参考

- [Spring Boot集成持久化Quartz定时任务管理和界面展示](https://www.cnblogs.com/dekevin/p/8716596.html)
- [guns-lite](https://gitee.com/enilu/guns-lite)

### 源码

 - 码云：<https://gitee.com/leiguoqing/springboot-quartz-lite>
 - Github：<https://github.com/MRLEILOVE/springboot-quartz-lite.git>

### 其它

 - [Quartz集群原理及配置应用](https://www.cnblogs.com/zhenyuyaodidiao/p/4755649.html)
