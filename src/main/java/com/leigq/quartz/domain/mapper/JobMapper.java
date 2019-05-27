package com.leigq.quartz.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 任务 Mapper
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
public interface JobMapper {
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
    String getJobGroup(@Param("jobClsName")String jobClsName);
}
