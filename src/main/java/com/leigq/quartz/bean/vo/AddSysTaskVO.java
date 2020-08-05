package com.leigq.quartz.bean.vo;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.leigq.quartz.util.ValidUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加任务接受参数 VO
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/22 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddSysTaskVO implements Serializable {

    private static final long serialVersionUID = 7803099260867270252L;

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

    /**
     * 执行参数，前端类似这样传 aaa=11;bbb=222
     */
    private String execParams;

    /**
     * 是否禁用，0(false)：禁用 1（true）：启用
     */
    private Boolean disabled;

    /**
     * 是否允许并发，0(false)：不允许 1（true）：允许
     */
    private Boolean concurrent;


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
        Map<String, Object> dataMap = new HashMap<>();

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
