package com.leigq.quartz.domain.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leigq.quartz.bean.vo.JobAndTriggerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Quartz任务自带表 Mapper
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019/5/28 2:53 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Mapper
public interface QuartzJobMapper {
    /**
     * 获取任务分组
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 2:51 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     */
    String getJobGroupName(@Param("jobClsName")String jobClsName);


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
    IPage<JobAndTriggerVO> getJobAndTriggerDetails(@Param("page") Page<JobAndTriggerVO> page);
}
