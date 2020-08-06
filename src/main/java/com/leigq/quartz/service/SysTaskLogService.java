package com.leigq.quartz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leigq.quartz.domain.entity.SysTaskLog;
import com.leigq.quartz.domain.mapper.SysTaskLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type Sys task log service.
 * @author leigq
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SysTaskLogService extends ServiceImpl<SysTaskLogMapper, SysTaskLog> {

    /**
     * Task log list page.
     *
     * @param taskId   the task id
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the page
     */
    public IPage<SysTaskLog> taskLogList(Long taskId, Integer pageNum, Integer pageSize) {
        IPage<SysTaskLog> page = new Page<>(pageNum, pageSize);
        return this.page(page, Wrappers.<SysTaskLog>lambdaQuery().eq(SysTaskLog::getTaskId, taskId));
    }

}
