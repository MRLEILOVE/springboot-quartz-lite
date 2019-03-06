package com.leigq.quartz.task;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * ClassName
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019-03-06 14:32 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
public class CronScheduler {

    public static void main(String[] args) throws SchedulerException {
        cronScheduler();
    }

    public static void cronScheduler() throws SchedulerException {
        /*
         * JobBuilder.newJob(HiJob.class)  --> 创建执行的job实现类
         * withIdentity("j1", "1") 创建该JobDetail 的名字和所在分组 第一个参数是名字, 第二个参数 分组
         * build()创建对象
         */
        JobDetail build = JobBuilder.newJob(TestTask1.class).withIdentity("j1", "1").build();

        Date date = new Date();
        long startTime = date.getTime() + 3000;

        /*
         * TriggerBuilder.newTrigger() 创建一个Trigger
         * startAt(new Date(startTime)) 执行时间, 3秒后执行
         * withIdentity("t1", "1") Trigger所在的名字和分组,
         * 第一个参数是名字, 第二个参数 分组,因为和job是俩个对象, 名字 分组可以跟job重复
         * withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?")) cron表达式初始化, 每秒执行一次
         * build()创建对象
         */
        CronTrigger c = TriggerBuilder.newTrigger()
                .startAt(new Date(startTime))
                .withIdentity("t1", "1")
                .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?"))
                .build();
        //创建SchedulerFactory对象  注意是new的是StdSchedulerFactory Std开头
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //获得Scheduler
        Scheduler scheduler = schedulerFactory.getScheduler();
        //设置调度 的job 和trigger
        scheduler.scheduleJob(build, c);
        //开启调度
        scheduler.start();
    }

}
