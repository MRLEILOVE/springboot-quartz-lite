package com.leigq.quartz.domain.mapper;

import com.leigq.quartz.domain.entity.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface JobAndTriggerMapper {
	List<JobAndTrigger> getJobAndTriggerDetails();
}
