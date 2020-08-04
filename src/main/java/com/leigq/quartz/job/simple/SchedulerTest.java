package com.leigq.quartz.job.simple;

import com.leigq.quartz.job.HelloQuartz2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 测试最简单的Quartz
 * <br/>
 * 参考:
 * <ul>
 *      <li>
 *          <a href="http://www.importnew.com/22890.html">Quartz 入门详解</a>
 *      </li>
 *      <li>
 *          主要看这个：<a href="https://www.cnblogs.com/drift-ice/p/3817269.html">Quartz使用总结</a>
 *      </li>
 * </ul>
 *
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019-03-05 16:31 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
public class SchedulerTest {

    public static void main(String[] args) throws SchedulerException {
        // 创建jobDetail实例，绑定Job实现类
        // 指明job的名称，所在组的名称，以及绑定job类
        JobDetail jobDetail = JobBuilder.newJob(HelloQuartz2.class)
                .withIdentity("helloJob", "helloJobGroup")
                .usingJobData("jobDateKey", "jobDateValue")//定义属性
                .withDescription("测试Quartz")
                .build();

        //定义调度触发规则
        //corn表达式  每五秒执行一次
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("CronTrigger", "CronTriggerGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .startNow()//一旦加入scheduler，立即生效
                .build();

        //通过schedulerFactory获取一个调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 把job和触发器注册到任务调度中
        scheduler.scheduleJob(jobDetail, cronTrigger);

        // 启动调度
        scheduler.start();

//        Thread.sleep(10000);

        // 停止调度
//        scheduler.shutdown();
    }

}
