package com.leigq.quartz;

import com.leigq.quartz.service.JobService;
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
    private JobService jobService;

    @Test
    public void contextLoads() throws InterruptedException {

        System.out.println(jobService.pauseJob(HelloQuartz2.class));

        Thread.sleep(5000);

        System.out.println(jobService.resumeJob(HelloQuartz2.class));

    }

}
