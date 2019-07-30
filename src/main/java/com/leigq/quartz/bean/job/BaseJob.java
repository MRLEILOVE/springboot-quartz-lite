package com.leigq.quartz.bean.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Job基础接口，其他Job实现此接口
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019-03-06 16:02 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
public interface BaseJob extends Job {
    @Override
    void execute(JobExecutionContext context) throws JobExecutionException;
}
