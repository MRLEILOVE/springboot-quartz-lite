package com.leigq.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.leigq.quartz.domain.entity.JobAndTrigger;
import com.leigq.quartz.job.BaseJob;
import com.leigq.quartz.service.JobAndTriggerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/job")
public class JobController {

    private final JobAndTriggerService jobAndTriggerService;
    private final Scheduler scheduler;

    @Autowired
    public JobController(JobAndTriggerService jobAndTriggerService, Scheduler scheduler) {
        this.jobAndTriggerService = jobAndTriggerService;
        this.scheduler = scheduler;
    }


    @PostMapping(value = "/addjob")
    public void addjob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        // 启动调度器
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
                .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }


    @PostMapping(value = "/pausejob")
    public void pausejob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    @PostMapping(value = "/resumejob")
    public void resumejob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }


    @PostMapping(value = "/deletejob")
    public void deletejob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    @GetMapping(value = "/queryjob")
    public Map<String, Object> queryjob(Integer pageNum, Integer pageSize) {
        PageInfo<JobAndTrigger> jobAndTrigger = jobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }

    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }


}
