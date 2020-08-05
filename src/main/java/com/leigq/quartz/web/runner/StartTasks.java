package com.leigq.quartz.web.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动定时任务
 *
 * @author leigq
 * @date 2020-08-04
 */
@Component
public class StartTasks implements ApplicationRunner {

//    @Autowired
//    private JobService jobService;
//
//    private final Logger log = LoggerFactory.getLogger(getClass());
//
//    @Override
//    public void run(ApplicationArguments applicationArguments) {
//        log.info("start Job >>>>>>>>>>>>>>>>>>>>>>>");
//        List<QuartzJob> list = jobService.getTaskList();
//        for (QuartzJob quartzJob : list) {
//            jobService.addJob(quartzJob);
//        }
//    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
