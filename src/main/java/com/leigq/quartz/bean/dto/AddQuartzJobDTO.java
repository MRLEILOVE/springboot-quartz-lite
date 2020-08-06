package com.leigq.quartz.bean.dto;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.google.common.collect.Maps;
import com.leigq.quartz.util.ValidUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加 Quartz 任务表 DTO
 * @author leigq
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
     * 是否允许并发，0(false)：不允许 1（true）：允许
     */
    private Boolean concurrent;

    /**
     * 执行参数
     */
    private Map<String, Object> dataMap;


    /**
     * 执行参数转换为 Map
     *
     * @param execParams the exec params
     * @return the map
     */
    public Map<String, Object> transExecParams(String execParams) {
        if (StringUtils.isEmpty(execParams)) {
            return null;
        }

        ValidUtils.checkArg(!execParams.contains("="), "执行参数格式错误");

        // 转换执行参数为 Map
        Map<String, Object> dataMap = Maps.newHashMapWithExpectedSize(5);

        // 判断是多个参数还是单个参数
        if (execParams.contains(";")) {
            // 多个参数
            final String[] params = StringUtils.split(execParams, ";");
            if (ArrayUtils.isEmpty(params)) {
                return null;
            }
            for (String param : params) {
                final String[] p = StringUtils.split(param, "=");
                if (ArrayUtils.isEmpty(p)) {
                    continue;
                }
                dataMap.put(p[0], p[1]);
            }
        } else {
            final String[] params = StringUtils.split(execParams, "=");
            if (ArrayUtils.isEmpty(params)) {
                return null;
            }
            dataMap.put(params[0], params[1]);
        }
        return dataMap;
    }
}
