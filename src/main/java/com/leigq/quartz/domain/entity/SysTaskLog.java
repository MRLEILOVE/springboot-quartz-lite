package com.leigq.quartz.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @TableField(value = "task_name")
    private String taskName;

    /**
     * 执行时间
     */
    @TableField(value = "exec_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date execDate;

    /**
     * 执行结果（成功:1、失败:0)
     */
    @TableField(value = "exec_result")
    private Integer execResult;

    /**
     * 抛出的异常信息
     */
    @TableField(value = "exec_result_text")
    private String execResultText;

    /**
     * 任务ID，外键
     */
    @TableField(value = "task_id")
    private Long taskId;

    private static final long serialVersionUID = 1L;
}