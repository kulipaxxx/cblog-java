package com.cheng.config;

import com.cheng.utils.LikeTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: QuartzConfig
 * @Author cheng
 * @Date: 2022/11/23 15:07
 * @Version 1.0
 */
@Configuration
public class QuartzConfig {

    private static final String LIKE_TASK_IDENTITY = "LikeTaskQuartz";

    @Bean
    public JobDetail quartzDetail(){
        return JobBuilder.newJob(LikeTask.class).withIdentity(LIKE_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger quartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10)  //设置时间周期单位秒
                .withIntervalInHours(2)  //两个小时执行一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(quartzDetail())
                .withIdentity(LIKE_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }
}

