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
 * 任务表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_task")
public class SysTask implements Serializable {
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名
     */
    @TableField(value = "job_name")
    private String jobName;

    /**
     * 任务组
     */
    @TableField(value = "job_group")
    private String jobGroup;

    /**
     * 执行类
     */
    @TableField(value = "job_class")
    private String jobClass;

    /**
     * 任务说明
     */
    @TableField(value = "note")
    private String note;

    /**
     * 定时规则
     */
    @TableField(value = "cron")
    private String cron;

    /**
     * 执行参数
     */
    @TableField(value = "exec_params")
    private String execParams;

    /**
     * 执行时间
     */
    @TableField(value = "exec_at")
    private Date execAt;

    /**
     * 执行结果
     */
    @TableField(value = "exec_result")
    private String execResult;

    /**
     * 是否禁用
     */
    @TableField(value = "disabled")
    private Boolean disabled;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "creator")
    private Long creator;

    /**
     * 是否允许并发
     */
    @TableField(value = "concurrent")
    private Integer concurrent;

    private static final long serialVersionUID = 1L;
}