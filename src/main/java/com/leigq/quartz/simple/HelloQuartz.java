package com.leigq.quartz.simple;

import com.leigq.quartz.job.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

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
public class HelloQuartz implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) {
        log.warn(">>>>>>>>>Hello Quartz start!");
        final JobDetail jobDetail = context.getJobDetail();
        final JobDataMap jobDataMap = jobDetail.getJobDataMap();
        final String value = jobDataMap.getString("jobDateKey");
        log.warn("value:{}", value);
    }
}
