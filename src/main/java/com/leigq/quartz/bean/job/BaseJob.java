package com.leigq.quartz.bean.job;

import com.leigq.quartz.bean.dto.AddQuartzJobDTO;
import com.leigq.quartz.bean.dto.TaskExecuteDTO;
import com.leigq.quartz.util.SpringContextHolder;
import com.leigq.quartz.web.exception.ServiceException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

/**
 * 任务基类，所有任务都继承此类
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019-03-06 16:02 <br>
 */
public class BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        // 获取 Job 中定义属性 （调用任务的时候传递进来的参数）
        JobDataMap data = context.getJobDetail().getJobDataMap();

        // 获取创建任务时传递过来的参数
        AddQuartzJobDTO quartzJobDetails = (AddQuartzJobDTO) data.get("quartzJobDetails");

        try {
            // 获取自定义任务的类
            Class<?> clazz = Class.forName(quartzJobDetails.getTaskClass());
            // 获取自定义任务实例，自定义任务全部继承 TaskExecute
            BaseTaskExecute baseTaskExecute = (BaseTaskExecute) SpringContextHolder.getBean(clazz);

            TaskExecuteDTO taskExecuteDTO = TaskExecuteDTO.builder().build();
            BeanUtils.copyProperties(quartzJobDetails, taskExecuteDTO);

            baseTaskExecute.setTaskExecuteDTO(taskExecuteDTO);
            baseTaskExecute.execute();
        } catch (Exception e) {
            String errorMessage = String.format("任务: [%s] 未启动成功，请检查执行类是否配置正确！！！", quartzJobDetails.getTaskName());
            throw new ServiceException(errorMessage, e);
        }
    }
}
