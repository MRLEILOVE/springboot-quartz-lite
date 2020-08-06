package com.leigq.quartz.bean.constant;

/**
 * Quartz 的 Trigger 触发器相关常量
 * @author leigq
 */
public interface QuartzTriggerConstant {

    String TRIGGER_NAME_FORMAT = "%sTrigger";

    static String triggerName(String prefix) {
        return String.format(TRIGGER_NAME_FORMAT, prefix);
    }

}
