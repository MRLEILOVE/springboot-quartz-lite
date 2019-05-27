package com.leigq.quartz.service;

import com.leigq.quartz.domain.mapper.JobMapper;
import com.leigq.quartz.job.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务服务
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019/5/28 2:52 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Service
@Transactional
@Slf4j
public class JobService {

    private final Scheduler scheduler;
    private final JobMapper jobMapper;

    @Autowired
    public JobService(JobMapper jobMapper, Scheduler scheduler) {
        this.scheduler = scheduler;
        this.jobMapper = jobMapper;
    }

    /**
     * 添加任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:20 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param jobClassName   任务类全名
     * @param jobGroupName   任务组名
     * @param cronExpression 任务表达式
     * @param jobDescription 任务描述
     */
    public Boolean addJob(String jobClassName, String jobGroupName, String cronExpression, String jobDescription) {

        // 验证表达式格式
        if (!CronExpression.isValidExpression(cronExpression)) {
            throw new RuntimeException("表达式格式错误！");
        }

        BaseJob baseJob;
        try {
            // 利用反射获取任务实例，因为所有任务都是实现BaseJob的接口，所以这里使用BaseJob接收
            baseJob = (BaseJob) Class.forName(jobClassName).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("全类名错误！");
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
            return true;
        } catch (SchedulerException e) {
            log.error("创建定时任务失败:", e);
            return false;
        }
    }

    /**
     * 暂停任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 2:57 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param cls 任务全类名，可使用类.class获得
     * @return true 成功, false 失败
     */
    public Boolean pauseJob(Class<?> cls) {
        return pauseJob(cls.getSimpleName(), jobMapper.getJobGroup(cls.getName()));
    }

    /**
     * 暂停任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 2:57 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param jobSimpleName 类名
     * @param jobGroupName  类组名
     * @return true 成功, false 失败
     */
    public Boolean pauseJob(String jobSimpleName, String jobGroupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobSimpleName, jobGroupName));
            return true;
        } catch (SchedulerException e) {
            log.error("暂停任务异常", e);
            return false;
        }
    }

    /**
     * 恢复任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:00 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param cls 任务全类名，可使用类.class获得
     * @return true 成功, false 失败
     */
    public Boolean resumeJob(Class<?> cls) {
        return resumeJob(cls.getSimpleName(), jobMapper.getJobGroup(cls.getName()));
    }


    /**
     * 恢复任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:00 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param jobSimpleName 类名
     * @param jobGroupName  类组名
     * @return true 成功, false 失败
     */
    public Boolean resumeJob(String jobSimpleName, String jobGroupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobSimpleName, jobGroupName));
            return true;
        } catch (SchedulerException e) {
            log.error("恢复任务异常", e);
            return false;
        }
    }

    /**
     * 更新任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:50 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param jobClassName   任务全类名
     * @param jobGroupName   类组名
     * @param cronExpression 任务表达式
     * @return true 成功, false 失败
     */
    public Boolean rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) {
        // 验证表达式格式
        if (!CronExpression.isValidExpression(cronExpression)) {
            throw new RuntimeException("表达式格式错误！");
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
            return true;
        } catch (SchedulerException e) {
            log.error("更新任务失败", e);
            return false;
        }
    }

    /**
     * 更新任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:50 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param cls            任务类，可用 类名.class 获得
     * @param cronExpression 表达式
     * @return true 成功, false 失败
     */
    public Boolean rescheduleJob(Class<?> cls, String cronExpression) {
        return rescheduleJob(cls.getName(), jobMapper.getJobGroup(cls.getName()), cronExpression);
    }


    /**
     * 删除任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:53 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param jobSimpleName 任务类名
     * @param jobGroupName  类组名
     * @return true 成功, false 失败
     */
    public Boolean deleteJob(String jobSimpleName, String jobGroupName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobSimpleName, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobSimpleName, jobGroupName));
            scheduler.deleteJob(JobKey.jobKey(jobSimpleName, jobGroupName));
            return true;
        } catch (SchedulerException e) {
            log.error("删除任务异常", e);
            return false;
        }
    }

    /**
     * 删除任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:53 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param cls 任务类，可用 类名.class 获得
     * @return true 成功, false 失败
     */
    public Boolean deleteJob(Class<?> cls) {
        return deleteJob(cls.getSimpleName(), jobMapper.getJobGroup(cls.getName()));
    }

}
