package com.leigq.quartz.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 更新任务 VO
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/22 16:53
 */
@Data
public class UpdateSysTaskVO implements Serializable {

    private static final long serialVersionUID = 7803099260867270252L;


    @NotNull
    @Size(min = 1)
    private Long taskId;

    /**
     * 任务名
     */
    @NotEmpty(message = "任务名不能为空！")
    private String taskName;

    /**
     * 任务组
     */
    @NotEmpty(message = "任务分组不能为空！")
    private String taskGroup;

    /**
     * 执行类
     */
    @NotEmpty(message = "全类名不能为空！")
    private String taskClass;

    /**
     * 任务说明
     */
    @NotEmpty(message = "任务说明不能为空！")
    private String note;

    /**
     * 定时规则
     */
    @NotEmpty(message = "定时规则(表达式)不能为空！")
    private String cron;

}
