package com.zkl.l_music.util;

import com.zkl.l_music.service.CommentsLikeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 点赞定时任务
 */
@Slf4j
public class LikeTask extends QuartzJobBean {

    @Autowired
    CommentsLikeService commentsLikeService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("LikeTask---start----- {}", sdf.format(new Date()));

        //将 Redis 里的点赞信息同步到数据库里
        commentsLikeService.getLikedsFromRedisToDB();
        log.info("LikeTask---end----- {}", sdf.format(new Date()));
    }
}
