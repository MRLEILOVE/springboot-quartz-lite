package com.leigq.quartz.task;

import com.leigq.quartz.bean.job.BaseTaskExecute;
import com.leigq.quartz.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

/**
 * 最简单的Quartz
 *
 * @author leigq <br>
 * @date ：2019-03-05 16:28 <br>
 */
@Slf4j
@Component
public class HelloQuartz1 extends BaseTaskExecute implements Serializable {

    /**
     * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
     */
    private static final long serialVersionUID = 8969855105016200770L;

    @Autowired
    private QuartzJobService quartzJobService;

    /**
     * Execute.
     *
     * @param dataMap the data map
     */
    @Override
    public void execute(Map<String, Object> dataMap) {
        // */2 * * * * ?
        log.warn(">>>>>>>>>Hello Quartz1 start!");
        log.warn("quartzJobService = {}", quartzJobService);
    }
}
