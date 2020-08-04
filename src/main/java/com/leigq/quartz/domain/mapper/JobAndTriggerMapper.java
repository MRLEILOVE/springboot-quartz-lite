package com.leigq.quartz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leigq.quartz.domain.entity.JobAndTrigger;
import org.apache.ibatis.annotations.Param;

public interface JobAndTriggerMapper extends BaseMapper<JobAndTrigger> {
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
	IPage<JobAndTrigger> getJobAndTriggerDetails(@Param("page") Page<JobAndTrigger> page);


}
