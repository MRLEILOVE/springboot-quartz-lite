package com.leigq.quartz.bean.job;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

/**
 * 不并发执行的任务基类，继承自 BaseJob
 */
@Component
@DisallowConcurrentExecution
public class BaseJobDisallowConcurrent extends BaseJob {

}
