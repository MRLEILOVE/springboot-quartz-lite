package com.leigq.quartz.domain.mapper;

import com.leigq.quartz.domain.entity.JobAndTrigger;

import java.util.List;

public interface JobAndTriggerMapper {
	/**
	 * 获取任务与触发器详细信息
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 14:00 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	List<JobAndTrigger> getJobAndTriggerDetails();


}
