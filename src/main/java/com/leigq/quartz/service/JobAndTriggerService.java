package com.leigq.quartz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leigq.quartz.domain.entity.JobAndTrigger;
import com.leigq.quartz.domain.mapper.JobAndTriggerMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@Slf4j
public class JobAndTriggerService {

	private final JobAndTriggerMapper jobAndTriggerMapper;
	private final Scheduler scheduler;

	@Autowired
	public JobAndTriggerService(JobAndTriggerMapper jobAndTriggerMapper, Scheduler scheduler) {
		this.jobAndTriggerMapper = jobAndTriggerMapper;
		this.scheduler = scheduler;
	}

	/**
	 * 获取任务与触发器详细信息
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 1:18 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
		return new PageInfo<>(list);
	}

}