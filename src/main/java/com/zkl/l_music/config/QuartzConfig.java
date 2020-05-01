package com.zkl.l_music.config;

import com.zkl.l_music.util.LikeTask;
import com.zkl.l_music.util.RecommentTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    private static final String LIKE_TASK_IDENTITY = "LikeTaskQuartz";
    private static final String RECOMMENT_TASK_IDENTITY = "RecommentTaskQuartz";

    @Bean
    public JobDetail quartzLikeDetail(){
        return JobBuilder.newJob(LikeTask.class).withIdentity(LIKE_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger quartzLikeTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(10)  //设置时间周期单位秒
                .withIntervalInHours(2)  //两个小时执行一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(quartzLikeDetail())
                .withIdentity(LIKE_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail quartzRecommentDetail(){
        return JobBuilder.newJob(RecommentTask.class).withIdentity(RECOMMENT_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger quartzRecommentTrigger(){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 1 ? * L");
        return TriggerBuilder.newTrigger().forJob(quartzRecommentDetail())
                .withIdentity(RECOMMENT_TASK_IDENTITY)
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
