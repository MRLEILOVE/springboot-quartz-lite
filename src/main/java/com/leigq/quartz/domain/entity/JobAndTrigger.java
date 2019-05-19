package com.leigq.quartz.domain.entity;

import lombok.Data;

import java.math.BigInteger;


/**
 * 任务与触发器结合实体，主要用于查询任务返回给前端
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019/5/19 1:14 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Data
public class JobAndTrigger {

	private String jobName;
	private String jobGroup;
	private String jobDescription;
	private String jobClassName;
	private String triggerName;
	private String triggerGroup;
	private BigInteger repeatInterval;
	private BigInteger timesTriggered;
	private String prevFireTime;
	private String nextFireTime;
	private String cronExpression;
	private String timeZoneId;
	private String triggerState;
	
}
