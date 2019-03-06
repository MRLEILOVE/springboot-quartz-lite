package com.leigq.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 简单的执行任务, 打印时间和你好
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019-03-06 11:03 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Slf4j
public class TestTask1 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //这里为了演示, 只进行打印
        log.warn("简单的执行任务:{}", nowTime + "Hello");
    }
}
