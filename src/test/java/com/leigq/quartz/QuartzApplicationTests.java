package com.leigq.quartz;

import com.leigq.quartz.service.QuartzJobService;
import com.leigq.quartz.job.HelloQuartz2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartzApplicationTests {

    @Autowired
    private QuartzJobService quartzJobService;

    @Test
    public void contextLoads() throws InterruptedException {

        System.out.println(quartzJobService.pauseJob(HelloQuartz2.class));

        Thread.sleep(5000);

        System.out.println(quartzJobService.resumeJob(HelloQuartz2.class));

    }

}
