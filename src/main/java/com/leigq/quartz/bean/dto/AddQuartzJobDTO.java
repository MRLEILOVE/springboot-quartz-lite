package com.leigq.quartz.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 添加 Quartz 任务表 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddQuartzJobDTO implements Serializable {
    private Long taskId;
    private String taskName;
    private String taskGroup;
    private String taskClass;
    private String note;
    private String cron;

    /**
     * 是否启用，0(false)：禁用 1（true）：启用
     */
    private Boolean enabled;

    /**
     * 是否允许并发，0(false)：不允许 1（true）：允许
     */
    private Boolean concurrent;

    /**
     * 执行参数
     */
    private Map<String, Object> dataMap;
}
