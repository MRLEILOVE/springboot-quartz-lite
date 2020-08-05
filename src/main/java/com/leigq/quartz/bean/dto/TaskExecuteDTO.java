package com.leigq.quartz.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 给 TaskExecute 内部使用的
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskExecuteDTO implements Serializable {
    private Long taskId;
    private String taskName;

    /**
     * 执行参数
     */
    private Map<String, Object> dataMap;
}
