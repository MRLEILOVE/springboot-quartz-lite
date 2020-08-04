package com.leigq.quartz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leigq.quartz.domain.entity.JobAndTrigger;
import com.leigq.quartz.domain.mapper.JobAndTriggerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class JobAndTriggerService extends ServiceImpl<JobAndTriggerMapper, JobAndTrigger> {

	private final JobAndTriggerMapper jobAndTriggerMapper;

	public JobAndTriggerService(JobAndTriggerMapper jobAndTriggerMapper) {
		this.jobAndTriggerMapper = jobAndTriggerMapper;
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
	public IPage<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
		Page<JobAndTrigger> page = new Page<>(pageNum, pageSize);
		return jobAndTriggerMapper.getJobAndTriggerDetails(page);
	}

}

