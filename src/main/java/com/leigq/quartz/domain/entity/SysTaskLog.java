package com.leigq.quartz.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务执行日志表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_task_log")
public class SysTaskLog implements Serializable {
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 执行时间
     */
    @TableField(value = "exec_at")
    private Date execAt;

    /**
     * 执行结果（成功:1、失败:0)
     */
    @TableField(value = "exec_success")
    private Integer execSuccess;

    /**
     * 抛出的异常信息
     */
    @TableField(value = "job_exception")
    private String jobException;

    /**
     * 任务ID，外键
     */
    @TableField(value = "id_task")
    private Long idTask;

    private static final long serialVersionUID = 1L;
}