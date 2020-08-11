package com.leigq.quartz.bean.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.leigq.quartz.bean.dto.TaskExecuteDTO;
import com.leigq.quartz.bean.enumeration.SysTaskExecResultEnum;
import com.leigq.quartz.domain.entity.SysTask;
import com.leigq.quartz.domain.entity.SysTaskLog;
import com.leigq.quartz.service.SysTaskLogService;
import com.leigq.quartz.service.SysTaskService;
import com.leigq.quartz.util.EmailSender;
import com.leigq.quartz.util.ExceptionDetailUtils;
import com.leigq.quartz.web.properties.QuartzProperties;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务执行的抽象类（模板），自己的任务继承此类，重写 execute 方法来执行自己的任务
 * @author leigq
 */
@Component
public abstract class BaseTaskExecute {

    private final Logger log = LoggerFactory.getLogger(BaseTaskExecute.class);

    private TaskExecuteDTO taskExecuteDTO;

    @Autowired
    private SysTaskService sysTaskService;

    @Autowired
    private SysTaskLogService sysTaskLogService;

    @Autowired
    private QuartzProperties quartzProperties;

    @Autowired
    private EmailSender emailSender;


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
            final String errorMsg = String.format("任务：[ %s ] 执行异常：", taskExecuteDTO.getTaskName());
            log.error(errorMsg, e);
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
            // 发送邮件
            if (quartzProperties.getMail().getEnable()) {
                final List<String> receiveUsername = quartzProperties.getMail().getReceiveUsername();
                final String[] usernames = receiveUsername.toArray(new String[0]);
                emailSender.sendSimpleMail("任务执行异常", errorMsg + ExceptionDetailUtils.getThrowableDetail(e), usernames);
            }
            // 回滚事务
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } catch (NoTransactionException noTransactionException) {
                // 没事务不回滚
                log.warn("没事务不回滚");
            }
        }
    }

    /**
     * 自定义任务子类需实现此方法
     *
     * @param dataMap 添加任务时配置的参数，如：aaa=111;bbb=222
     * @throws Exception the exception
     */
    public abstract void execute(Map<String, Object> dataMap) throws Exception;
}
