package com.leigq.quartz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leigq.quartz.bean.dto.AddQuartzJobDTO;
import com.leigq.quartz.bean.vo.AddSysTaskVO;
import com.leigq.quartz.bean.vo.SysTaskListVO;
import com.leigq.quartz.bean.vo.UpdateSysTaskVO;
import com.leigq.quartz.domain.entity.SysTask;
import com.leigq.quartz.domain.mapper.SysTaskMapper;
import com.leigq.quartz.util.ValidUtils;
import com.leigq.quartz.web.exception.ServiceException;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 系统自己创建的任务表服务
 */
@Transactional
@Service
public class SysTaskService extends ServiceImpl<SysTaskMapper, SysTask> {

    /**
     * Quartz自带表的任务服务
     */
    private final QuartzJobService quartzJobService;
    private final SysTaskMapper sysTaskMapper;

    public SysTaskService(QuartzJobService quartzJobService, SysTaskMapper sysTaskMapper) {
        this.quartzJobService = quartzJobService;
        this.sysTaskMapper = sysTaskMapper;
    }

    /**
     * 添加任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:20 <br>
     *
     * @param addSysTaskVO 添加任务接受参数 VO
     */
    public void addTask(AddSysTaskVO addSysTaskVO) {
        try {
            // 先添加一条任务记录到自己的任务表，应该后面任务日志需要任务id
            SysTask sysTask = SysTask.builder().build();
            BeanUtils.copyProperties(addSysTaskVO, sysTask);
            sysTask.setCreateTime(new Date());
            // 创建人根据自己的系统确定，这里默认写死
            sysTask.setCreator(1L);
            this.save(sysTask);

            // 添加 Quartz 任务表
            AddQuartzJobDTO addQuartzJobDTO = AddQuartzJobDTO.builder().build();
            BeanUtils.copyProperties(addSysTaskVO, addQuartzJobDTO);
            // 转换执行参数为 Map
            addQuartzJobDTO.setDataMap(addQuartzJobDTO.transExecParams(addSysTaskVO.getExecParams()));
            addQuartzJobDTO.setTaskId(sysTask.getId());
            quartzJobService.addJob(addQuartzJobDTO);
        } catch (SchedulerException e) {
            throw new ServiceException("添加任务失败", e);
        }
    }


    /**
     * 更新任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:50 <br>
     *
     * @param updateSysTaskVO the update sys task vo
     */
    public void updateTask(UpdateSysTaskVO updateSysTaskVO) {
        final SysTask sysTask = this.getOne(Wrappers.<SysTask>lambdaQuery()
                .eq(SysTask::getTaskName, updateSysTaskVO.getTaskName())
                .eq(SysTask::getTaskGroup, updateSysTaskVO.getTaskGroup())
        );

        ValidUtils.isNull(sysTask, "查询不到此任务！");

        try {
            BeanUtils.copyProperties(updateSysTaskVO, sysTask);

            // 更新自定义任务表
            this.updateById(sysTask);

            // 更新 Quartz 框架表，只能先删除旧任务，在添加一个新任务
            quartzJobService.deleteJob(updateSysTaskVO.getTaskName(), updateSysTaskVO.getTaskGroup());

            AddQuartzJobDTO addQuartzJobDTO = AddQuartzJobDTO.builder().build();
            BeanUtils.copyProperties(updateSysTaskVO, addQuartzJobDTO);
            // 转换执行参数为 Map
            addQuartzJobDTO.setDataMap(addQuartzJobDTO.transExecParams(updateSysTaskVO.getExecParams()));
            addQuartzJobDTO.setTaskId(sysTask.getId());
            quartzJobService.addJob(addQuartzJobDTO);
        } catch (SchedulerException e) {
            throw new ServiceException("更新任务失败", e);
        }
    }


    /**
     * 执行任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 2:57 <br>
     *
     * @param jobSimpleName 类名
     * @param jobGroupName  类组名
     */
    public void executeTask(String jobSimpleName, String jobGroupName) {
        try {
            quartzJobService.executeJob(jobSimpleName, jobGroupName);
        } catch (SchedulerException e) {
            throw new ServiceException("执行任务失败", e);
        }
    }


    /**
     * 暂停任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 2:57 <br>
     *
     * @param jobSimpleName 类名
     * @param jobGroupName  类组名
     */
    public void pauseTask(String jobSimpleName, String jobGroupName) {
        try {
            quartzJobService.pauseJob(jobSimpleName, jobGroupName);
        } catch (SchedulerException e) {
            throw new ServiceException("暂停任务失败", e);
        }
    }


    /**
     * 恢复任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:00 <br>
     *
     * @param jobSimpleName 类名
     * @param jobGroupName  类组名
     */
    public void resumeTask(String jobSimpleName, String jobGroupName) {
        try {
            quartzJobService.resumeJob(jobSimpleName, jobGroupName);
        } catch (SchedulerException e) {
            throw new ServiceException("恢复任务失败", e);
        }
    }


    /**
     * 删除任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:53 <br>
     *
     * @param taskName  任务类名
     * @param taskGroup 类组名
     */
    public void deleteTask(String taskName, String taskGroup) {
        try {
            final SysTask sysTask = this.getOne(Wrappers.<SysTask>lambdaUpdate()
                    .eq(SysTask::getTaskName, taskName)
                    .eq(SysTask::getTaskGroup, taskGroup)
            );
            // 删除自定义任务表
            this.removeById(sysTask.getId());
            quartzJobService.deleteJob(taskName, taskGroup);
        } catch (SchedulerException e) {
            throw new ServiceException("删除任务失败", e);
        }
    }


    /**
     * 获取任务列表
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 1:18 <br>
     */
    public IPage<SysTaskListVO> taskList(int pageNum, int pageSize) {
        Page<SysTaskListVO> page = new Page<>(pageNum, pageSize);
        return sysTaskMapper.taskList(page);
    }

}
