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
    private Boolean disabled;
    private Boolean concurrent;

    /**
     * 执行参数
     */
    private Map<String, Object> dataMap;
}
