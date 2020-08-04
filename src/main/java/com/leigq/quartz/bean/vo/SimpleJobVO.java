package com.leigq.quartz.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 简单任务 VO，用户接受 执行任务、暂停任务、恢复任务  请求参数
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/22 16:53
 */
@Data
public class SimpleJobVO {

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
}
