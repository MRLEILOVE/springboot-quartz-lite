package com.leigq.quartz.bean.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.leigq.quartz.bean.dto.TaskExecuteDTO;
import com.leigq.quartz.bean.enumeration.SysTaskExecResultEnum;
import com.leigq.quartz.domain.entity.SysTask;
import com.leigq.quartz.domain.entity.SysTaskLog;
import com.leigq.quartz.service.SysTaskLogService;
import com.leigq.quartz.service.SysTaskService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.Map;

/**
 * 任务执行的抽象类（模板），自己的任务继承此类，重写 execute 方法来执行自己的任务
 */
@Component
public abstract class TaskExecute {

    private TaskExecuteDTO taskExecuteDTO;

    @Autowired
    private SysTaskService sysTaskService;

    @Autowired
    private SysTaskLogService sysTaskLogService;


    public void setTaskExecuteDTO(TaskExecuteDTO taskExecuteDTO) {
        this.taskExecuteDTO = taskExecuteDTO;
    }

    public void execute() {
        // 添加任务执行日志
        SysTaskLog sysTaskLog = SysTaskLog.builder()
                .taskName(taskExecuteDTO.getTaskName())
                .execDate(new Date())
                .execResult(SysTaskExecResultEnum.SUCCESS.getValue())
                .execResultText("任务执行成功")
                .taskId(taskExecuteDTO.getTaskId())
                .build();
        sysTaskLogService.save(sysTaskLog);

        // 更新任务的最近一次执行时间、最近一次执行结果字段
        sysTaskService.update(Wrappers.<SysTask>lambdaUpdate()
                .set(SysTask::getExecDate, new Date())
                .set(SysTask::getExecResult, SysTaskExecResultEnum.SUCCESS.getValue())
                .eq(SysTask::getId, taskExecuteDTO.getTaskId())
        );
        try {
            // 获取任务携带的参数
            Map<String, Object> dataMap = taskExecuteDTO.getDataMap();
            // 调用子类的任务
            execute(dataMap);
        } catch (Exception e) {
            // 将执行结果改为失败并记录异常信息
            sysTaskLogService.update(Wrappers.<SysTaskLog>lambdaUpdate()
                    .set(SysTaskLog::getExecResult, SysTaskExecResultEnum.FAILURE.getValue())
                    .set(SysTaskLog::getExecResultText, ExceptionUtils.getStackTrace(e))
                    .eq(SysTaskLog::getId, sysTaskLog.getId())
            );

            sysTaskService.update(Wrappers.<SysTask>lambdaUpdate()
                    .set(SysTask::getExecResult, SysTaskExecResultEnum.FAILURE.getValue())
                    .eq(SysTask::getId, taskExecuteDTO.getTaskId())
            );
            // 回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    /**
     * 自定义任务子类需实现此方法
     *
     * @param dataMap 添加任务时配置的参数，如：aaa=111;bbb=222
     */
    public abstract void execute(Map<String, Object> dataMap) throws Exception;
}
