package com.leigq.quartz.bean.job;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.leigq.quartz.bean.dto.TaskExecuteDTO;
import com.leigq.quartz.service.SysTaskLogService;
import com.leigq.quartz.service.SysTaskService;
import com.leigq.quartz.util.EmailSender;
import com.leigq.quartz.util.ExceptionDetailUtils;
import com.leigq.quartz.util.ThreadPoolUtils;
import com.leigq.quartz.web.properties.QuartzProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Map;
import java.util.Set;

/**
 * 任务执行的抽象类（模板），自己的任务继承此类，重写 execute 方法来执行自己的任务
 *
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


    /**
     * 自定义任务子类需实现此方法
     *
     * @param dataMap 添加任务时配置的参数，如：aaa=111;bbb=222
     * @throws Exception the exception
     */
    public abstract void execute(Map<String, Object> dataMap) throws Exception;


    /**
     * Execute.
     */
    public void execute() {
        // 添加任务执行成功日志
        final Long logId = sysTaskLogService.addSuccessLog(taskExecuteDTO.getTaskName(), taskExecuteDTO.getTaskId());

        // 更新任务的最近一次执行时间、最近一次执行结果字段，默认先成功
        sysTaskService.updateLatelyExecResultToSuccess(taskExecuteDTO.getTaskId());

        try {
            // 获取任务携带的参数
            Map<String, Object> dataMap = taskExecuteDTO.getDataMap();
            // 调用子类的任务
            this.execute(dataMap);
        } catch (Exception e) {
            this.log.error(String.format("任务：[ %s ] 执行异常：", taskExecuteDTO.getTaskName()), e);

            // 将执行结果改为失败并记录异常信息
            sysTaskLogService.updateExecResultToFail(logId, e);

            // 将任务最近一次执行结果改为失败
            sysTaskService.updateLatelyExecResultToFail(taskExecuteDTO.getTaskId());

            // 发送邮件
            sendEmail(e);
            // 回滚事务
            rollBackTransaction();
        }
    }


    /**
     * Send email.
     *
     * @param e the e
     */
    private void sendEmail(Exception e) {
        if (!quartzProperties.getMail().getEnable()) {
            return;
        }
        final Set<String> receiveUsername = quartzProperties.getMail().getReceiveUsername();
        if (CollectionUtils.isEmpty(receiveUsername)) {
            return;
        }
        final String errorMsg = String.format("任务：[ %s ] 执行异常：", taskExecuteDTO.getTaskName());
        final String[] usernames = receiveUsername.toArray(new String[0]);

        ThreadPoolUtils.execute(() -> emailSender.sendSimpleMail("任务执行异常", errorMsg + ExceptionDetailUtils.getThrowableDetail(e), usernames));
    }

    /**
     * 事务回滚
     */
    private void rollBackTransaction() {
        try {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (NoTransactionException noTransactionException) {
            // 没事务不回滚
            log.warn("没事务不回滚");
        }
    }

}
