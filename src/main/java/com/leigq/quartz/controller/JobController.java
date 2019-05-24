package com.leigq.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.leigq.quartz.bean.Response;
import com.leigq.quartz.domain.entity.JobAndTrigger;
import com.leigq.quartz.job.BaseJob;
import com.leigq.quartz.service.JobAndTriggerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private final Response response;

    @Autowired
    public JobController(JobAndTriggerService jobAndTriggerService, Scheduler scheduler, Response response) {
        this.jobAndTriggerService = jobAndTriggerService;
        this.scheduler = scheduler;
        this.response = response;
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
    public Response addJob(String jobClassName, String jobGroupName, String cronExpression, String jobDescription) {
        if (StringUtils.isBlank(jobClassName)) {
            return response.failure("全类名不能为空！");
        }

        if (StringUtils.isBlank(jobGroupName)) {
            return response.failure("任务分组不能为空！");
        }

        if (StringUtils.isBlank(cronExpression)) {
            return response.failure("表达式不能为空！");
        }

        if (StringUtils.isBlank(jobDescription)) {
            return response.failure("任务描述不能为空！");
        }

        // 验证表达式格式
        if (!CronExpression.isValidExpression(cronExpression)) {
            return response.failure("表达式格式错误！");
        }

        BaseJob baseJob;
        try {
            // 利用反射获取任务实例，因为所有任务都是实现BaseJob的接口，所以这里使用BaseJob接收
            baseJob = (BaseJob) Class.forName(jobClassName).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            return response.failure("全类名错误！");
        }

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(baseJob.getClass())
                .withIdentity(baseJob.getClass().getSimpleName(), jobGroupName)
                .withDescription(jobDescription)
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
            return response.success("创建定时任务成功！");
        } catch (SchedulerException e) {
            log.error("创建定时任务失败:", e);
            return response.failure("创建定时任务失败！");
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
    public Response pauseJob(String jobClassName, String jobGroupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
            return response.success("暂停任务成功！");
        } catch (SchedulerException e) {
            log.error("暂停任务异常", e);
            return response.failure("暂停任务失败！");
        }
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
    public Response resumeJob(String jobClassName, String jobGroupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
            return response.success("恢复任务成功！");
        } catch (SchedulerException e) {
            log.error("恢复任务异常", e);
            return response.failure("恢复任务失败！");
        }

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
    @PostMapping("/jobs/action/update")
    public Response rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) {
        if (StringUtils.isBlank(cronExpression)) {
            return response.failure("表达式不能为空！");
        }
        // 验证表达式格式
        if (!CronExpression.isValidExpression(cronExpression)) {
            return response.failure("表达式格式错误！");
        }
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            return response.success("更新任务成功！");
        } catch (SchedulerException e) {
            log.error("更新任务失败", e);
            return response.failure("更新任务失败！");
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
    public Response deleteJob(String jobClassName, String jobGroupName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
            return response.success("删除任务成功！");
        } catch (SchedulerException e) {
            log.error("删除任务异常", e);
            return response.failure("删除任务失败！");
        }

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
    public Response queryJob(Integer pageNum, Integer pageSize) {
        PageInfo<JobAndTrigger> jobAndTrigger = jobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return response.success(map);
    }

}
