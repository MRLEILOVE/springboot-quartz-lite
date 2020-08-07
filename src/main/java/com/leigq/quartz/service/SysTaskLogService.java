package com.leigq.quartz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leigq.quartz.bean.vo.SysTaskLogListVO;
import com.leigq.quartz.domain.entity.SysTaskLog;
import com.leigq.quartz.domain.mapper.SysTaskLogMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * The type Sys task log service.
 *
 * @author leigq
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SysTaskLogService extends ServiceImpl<SysTaskLogMapper, SysTaskLog> {

    private final MapperFactory mapperFactory;

    public SysTaskLogService(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    /**
     * Task log list page.
     *
     * @param taskId   the task id
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the page
     */
    public IPage<SysTaskLogListVO> taskLogList(Long taskId, Integer pageNum, Integer pageSize) {
        IPage<SysTaskLog> page = this.page(new Page<>(pageNum, pageSize),
                Wrappers.<SysTaskLog>lambdaQuery().eq(SysTaskLog::getTaskId, taskId)
        );
        final MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        final List<SysTaskLogListVO> sysTaskLogListVOS = mapperFacade.mapAsList(page.getRecords(), SysTaskLogListVO.class);

        IPage<SysTaskLogListVO> pageResult = new Page<>();
        BeanUtils.copyProperties(page, pageResult);
        pageResult.setRecords(sysTaskLogListVOS);
        return pageResult;
    }

}
