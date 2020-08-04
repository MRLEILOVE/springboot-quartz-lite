package com.leigq.quartz.job;

import com.leigq.quartz.bean.job.BaseJob;
import com.leigq.quartz.service.JobAndTriggerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 最简单的Quartz
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019-03-05 16:28 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Slf4j
@Component
public class HelloQuartz2 implements BaseJob, Serializable {

    // 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
    private static final long serialVersionUID = 8969855105016200770L;

    @Autowired
    private JobAndTriggerService service;

    // */2 * * * * ?
    @Override
    public void execute(JobExecutionContext context) {
        log.warn(">>>>>>>>>Hello Quartz start!");
        final JobDetail jobDetail = context.getJobDetail();
        final JobDataMap jobDataMap = jobDetail.getJobDataMap();
        final String value = jobDataMap.getString("jobDateKey");
        log.warn("value:{}", value);
        // 测试是否获取到 bean
        log.error("service: {}", service);
    }
}
