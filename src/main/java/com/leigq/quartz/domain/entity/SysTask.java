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
import lombok.experimental.FieldNameConstants;

/**
 * 任务表
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_task")
@Data
@FieldNameConstants
public class SysTask implements Serializable {
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
     * 任务组
     */
    @TableField(value = "task_group")
    private String taskGroup;

    /**
     * 执行类
     */
    @TableField(value = "task_class")
    private String taskClass;

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
     * 最近一次执行时间
     */
    @TableField(value = "exec_date")
    private Date execDate;

    /**
     * 最近一次执行结果 （成功:1、失败:0)
     */
    @TableField(value = "exec_result")
    private Integer execResult;

    /**
     * 是否启用，0(false)：禁用 1（true）：启用
     */
    @TableField(value = "enabled")
    private Boolean enabled;

    /**
     * 是否允许并发，0(false)：不允许 1（true）：允许
     */
    @TableField(value = "concurrent")
    private Boolean concurrent;

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

    private static final long serialVersionUID = 1L;
}