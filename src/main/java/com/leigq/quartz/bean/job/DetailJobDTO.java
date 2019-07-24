package com.leigq.quartz.bean.job;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 详细任务 DTO，用户接受 添加任务、更新任务 请求参数
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/22 16:53
 */
@Data
public class DetailJobDTO {

	/**
	 * 任务全类名
	 */
	@NotEmpty(message = "全类名不能为空！")
	private String jobClassName;

	/**
	 * 任务组名
	 */
	@NotEmpty(message = "任务分组不能为空！")
	private String jobGroupName;

	/**
	 * 任务表达式
	 */
	@NotEmpty(message = "表达式不能为空！")
	private String cronExpression;

	/**
	 * 任务描述
	 */
	private String jobDescription;

	/**
	 * 密钥
	 */
	@NotEmpty(message = "密钥不能为空！")
	private String secretKey;


}
