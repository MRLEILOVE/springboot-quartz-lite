package com.leigq.quartz.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leigq.quartz.bean.vo.SysTaskListVO;
import com.leigq.quartz.domain.entity.SysTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysTaskMapper extends BaseMapper<SysTask> {
    /**
     * 获取任务列表
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the page
     */
    IPage<SysTaskListVO> taskList(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}