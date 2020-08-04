package com.leigq.quartz;

import com.leigq.quartz.job.HelloQuartz2;
import com.leigq.quartz.service.SysTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartzApplicationTests {

    @Autowired
    private SysTaskService sysTaskService;

    @Test
    public void contextLoads() throws InterruptedException {

        sysTaskService.pauseJob(HelloQuartz2.class);

        System.out.println();

        Thread.sleep(5000);

        sysTaskService.resumeJob(HelloQuartz2.class);

    }

}
