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


/**
 * Job Controller
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019/5/19 13:36 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Slf4j
@RestController
public class JobController {

    private final JobAndTriggerService jobAndTriggerService;
    private final Scheduler scheduler;

    @Autowired
    public JobController(JobAndTriggerService jobAndTriggerService, Scheduler scheduler) {
        this.jobAndTriggerService = jobAndTriggerService;
        this.scheduler = scheduler;
    }


    /**
     * 添加任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 13:37 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     */
    @PostMapping("/jobs")
    public void addjob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        // 利用反射获取任务实例，因为所有任务都是实现BaseJob的接口，所以这里使用BaseJob接收
        BaseJob baseJob = (BaseJob) Class.forName(jobClassName).newInstance();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(baseJob.getClass())
                .withIdentity(jobClassName, jobGroupName)
                .build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobClassName, jobGroupName)
                .withSchedule(scheduleBuilder)
                .startNow()
                .build();

        try {
            // 把job和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动调度器
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("创建定时任务失败:", e);
            throw new Exception("创建定时任务失败");
        }
    }


    /**
     * 暂停任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 13:48 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     */
    @PostMapping("/jobs/action/pause")
    public void pausejob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 恢复任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 13:48 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     */
    @PostMapping("/jobs/action/resume")
    public void resumejob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    /**
     * 更新任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 13:49 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     */
    @PutMapping("/jobs")
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
            log.error("更新定时任务失败", e);
            throw new Exception("更新定时任务失败");
        }
    }

    /**
     * 删除任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 13:49 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     */
    @DeleteMapping("/jobs")
    public void deletejob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 查询任务列表
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 13:49 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     */
    @GetMapping("/jobs")
    public Map<String, Object> queryJob(Integer pageNum, Integer pageSize) {
        PageInfo<JobAndTrigger> jobAndTrigger = jobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }

}
