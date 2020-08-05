package com.leigq.quartz.service;

import com.leigq.quartz.bean.dto.AddQuartzJobDTO;
import com.leigq.quartz.bean.job.BaseJob;
import com.leigq.quartz.bean.job.BaseJobDisallowConcurrent;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Quartz自带表的任务服务
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019/5/28 2:52 <br>
 */
@Slf4j
@Transactional
@Service
public class QuartzJobService {

    private final Scheduler scheduler;

    public QuartzJobService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 添加任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:20 <br>
     *
     * @param addQuartzJobDTO 添加 Quartz 任务表 DTO
     * @throws SchedulerException the scheduler exception
     */
    public void addJob(AddQuartzJobDTO addQuartzJobDTO) throws SchedulerException {
        //是否允许并发执行
        Class<? extends Job> jobClass = addQuartzJobDTO.getConcurrent() ? BaseJob.class : BaseJobDisallowConcurrent.class;

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(addQuartzJobDTO.getTaskName(), addQuartzJobDTO.getTaskGroup())
                .withDescription(addQuartzJobDTO.getNote())
                .build();

        // 向 BaseJob 中传递参数
        jobDetail.getJobDataMap().put("quartzJobDetails", addQuartzJobDTO);

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(addQuartzJobDTO.getCron());

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(addQuartzJobDTO.getTaskClass(), addQuartzJobDTO.getTaskGroup())
                .withSchedule(scheduleBuilder)
                .startNow()
                .build();

        // 把job和触发器注册到任务调度中
        scheduler.scheduleJob(jobDetail, trigger);
        // 启动调度器
        scheduler.start();
    }


    /**
     * 执行任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 2:57 <br>
     *
     * @param jobSimpleName 类名
     * @param jobGroupName  类组名
     */
    public void executeJob(String jobSimpleName, String jobGroupName) throws SchedulerException {
        scheduler.triggerJob(JobKey.jobKey(jobSimpleName, jobGroupName));
    }


    /**
     * 暂停任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 2:57 <br>
     *
     * @param jobSimpleName 类名
     * @param jobGroupName  类组名
     */
    public void pauseJob(String jobSimpleName, String jobGroupName) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobSimpleName, jobGroupName));
    }


    /**
     * 恢复任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:00 <br>
     *
     * @param jobSimpleName 类名
     * @param jobGroupName  类组名
     */
    public void resumeJob(String jobSimpleName, String jobGroupName) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobSimpleName, jobGroupName));
    }

    /**
     * 更新任务(只更新 cron 表达式)
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:50 <br>
     *
     * @param jobClassName   任务全类名
     * @param jobGroupName   类组名
     * @param cronExpression 任务表达式
     * @throws SchedulerException the scheduler exception
     */
    public void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
        // 表达式调度构建器
        // 增加：withMisfireHandlingInstructionDoNothing()方法 参考：https://blog.csdn.net/zhouhao1256/article/details/53486748?tdsourcetag=s_pctim_aiomsg
        // 1，不触发立即执行
        // 2，等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
    }


    /**
     * 删除任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:53 <br>
     *
     * @param jobSimpleName 任务类名
     * @param jobGroupName  类组名
     */
    public void deleteJob(String jobSimpleName, String jobGroupName) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobSimpleName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobSimpleName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobSimpleName, jobGroupName));
    }


    /**
     * 获取任务与触发器详细信息
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 1:18 <br>
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the job and trigger details
     */
//    public IPage<JobAndTriggerVO> getJobAndTriggerDetails(int pageNum, int pageSize) {
//        Page<JobAndTriggerVO> page = new Page<>(pageNum, pageSize);
//        return quartzJobMapper.getJobAndTriggerDetails(page);
//    }

}
