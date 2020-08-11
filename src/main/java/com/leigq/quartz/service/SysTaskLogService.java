package com.leigq.quartz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leigq.quartz.bean.enumeration.SysTaskExecResultEnum;
import com.leigq.quartz.bean.vo.SysTaskLogListVO;
import com.leigq.quartz.domain.entity.SysTaskLog;
import com.leigq.quartz.domain.mapper.SysTaskLogMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
                Wrappers.<SysTaskLog>lambdaQuery()
                        .eq(SysTaskLog::getTaskId, taskId)
                        .orderByDesc(SysTaskLog::getExecDate)
        );
        final MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        final List<SysTaskLogListVO> sysTaskLogList = mapperFacade.mapAsList(page.getRecords(), SysTaskLogListVO.class);

        IPage<SysTaskLogListVO> pageResult = new Page<>();
        BeanUtils.copyProperties(page, pageResult);
        pageResult.setRecords(sysTaskLogList);
        return pageResult;
    }


    /**
     * 添加一条任务执行成功日志
     *
     * @param taskName the task name
     * @param taskId   the task id
     * @return 日志ID
     */
    public Long addSuccessLog(String taskName, Long taskId) {
        // 添加任务执行日志
        SysTaskLog sysTaskLog = SysTaskLog.builder()
                .taskName(taskName)
                .execDate(new Date())
                .execResult(SysTaskExecResultEnum.SUCCESS.getValue())
                .execResultText("任务执行成功")
                .taskId(taskId)
                .build();
        this.save(sysTaskLog);
        return sysTaskLog.getId();
    }

    /**
     * 将执行结果改为失败并记录异常信息
     *
     * @param logId the log id
     * @param e     the e
     * @return the boolean
     */
    public boolean updateExecResultToFail(Long logId, Throwable e) {
        return this.update(Wrappers.<SysTaskLog>lambdaUpdate()
                .set(SysTaskLog::getExecResult, SysTaskExecResultEnum.FAILURE.getValue())
                .set(SysTaskLog::getExecResultText, ExceptionUtils.getStackTrace(e))
                .eq(SysTaskLog::getId, logId)
        );
    }

}
