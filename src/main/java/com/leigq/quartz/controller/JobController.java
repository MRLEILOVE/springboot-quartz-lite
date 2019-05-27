package com.leigq.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.leigq.quartz.bean.Response;
import com.leigq.quartz.domain.entity.JobAndTrigger;
import com.leigq.quartz.job.BaseJob;
import com.leigq.quartz.service.JobAndTriggerService;
import com.leigq.quartz.service.JobService;
import com.leigq.quartz.simple.HelloQuartz;
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

    // 密钥应该从数据库查询出来比对，这里暂时写死
    private static final String SECRET_KEY = "111111";

    private final JobAndTriggerService jobAndTriggerService;
    private final Response response;
    private final JobService jobService;

    @Autowired
    public JobController(JobAndTriggerService jobAndTriggerService, Response response, JobService jobService) {
        this.jobAndTriggerService = jobAndTriggerService;
        this.response = response;
        this.jobService = jobService;
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
    public Response addJob(String jobClassName, String jobGroupName, String cronExpression,
                           String jobDescription, String secretKey) {
        if (StringUtils.isBlank(jobClassName)) {
            return response.failure("全类名不能为空！");
        }

        if (StringUtils.isBlank(jobGroupName)) {
            return response.failure("任务分组不能为空！");
        }

        if (StringUtils.isBlank(jobDescription)) {
            return response.failure("任务描述不能为空！");
        }

        if (StringUtils.isBlank(cronExpression)) {
            return response.failure("表达式不能为空！");
        }

        if (StringUtils.isBlank(secretKey)) {
            return response.failure("密钥不能为空！");
        }

        if (!Objects.equals(SECRET_KEY, secretKey)) {
            return response.failure("密钥错误！");
        }

        if (jobService.addJob(jobClassName, jobGroupName, cronExpression, jobDescription)) {
            return response.success("创建定时任务成功！");
        }
        return response.failure("创建定时任务失败！");
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
    public Response pauseJob(String jobClassName, String jobGroupName, String secretKey) {
        if (StringUtils.isBlank(secretKey)) {
            return response.failure("密钥不能为空！");
        }
        if (!Objects.equals(SECRET_KEY, secretKey)) {
            return response.failure("密钥错误！");
        }
        if (jobService.pauseJob(jobClassName, jobGroupName)) {
            return response.success("暂停任务成功！");
        }
        return response.failure("暂停任务失败！");
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
    public Response resumeJob(String jobClassName, String jobGroupName, String secretKey) {
        if (StringUtils.isBlank(secretKey)) {
            return response.failure("密钥不能为空！");
        }
        if (!Objects.equals(SECRET_KEY, secretKey)) {
            return response.failure("密钥错误！");
        }
        if (jobService.resumeJob(jobClassName, jobGroupName)) {
            return response.success("恢复任务成功！");
        }
        return response.failure("恢复任务失败！");
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
    public Response rescheduleJob(String jobClassName, String jobGroupName,
                                  String cronExpression, String secretKey) {
        if (StringUtils.isBlank(cronExpression)) {
            return response.failure("表达式不能为空！");
        }
        // 验证表达式格式
        if (!CronExpression.isValidExpression(cronExpression)) {
            return response.failure("表达式格式错误！");
        }

        if (StringUtils.isBlank(secretKey)) {
            return response.failure("密钥不能为空！");
        }

        // 密钥应该从数据库查询出来比对，这里暂时写死
        if (!Objects.equals(SECRET_KEY, secretKey)) {
            return response.failure("密钥错误！");
        }

        if (jobService.rescheduleJob(jobClassName, jobGroupName, cronExpression)) {
            return response.success("更新任务成功！");
        }
        return response.failure("更新任务失败！");
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
    @DeleteMapping("/jobs/{jobClassName}/{jobGroupName}")
    public Response deleteJob(@PathVariable("jobClassName") String jobClassName,
                              @PathVariable("jobGroupName") String jobGroupName,
                              String secretKey) {
        if (StringUtils.isBlank(secretKey)) {
            return response.failure("密钥不能为空！");
        }

        // 密钥应该从数据库查询出来比对，这里暂时写死
        if (!Objects.equals(SECRET_KEY, secretKey)) {
            return response.failure("密钥错误！");
        }
        if (jobService.deleteJob(jobClassName, jobGroupName)) {
            return response.success("删除任务成功！");
        }
        return response.failure("删除任务失败！");
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
