package com.zkl.l_music.util;

import com.zkl.l_music.service.RecommentService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class RecommentTask extends QuartzJobBean {

    @Resource
    RecommentCF recommentCF;
    @Resource
    RecommentService recommentService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("RecommentTask---start----- {}", sdf.format(new Date()));
//        recommentCF.recommentUser();
        recommentService.insertNorRecomment();
        log.info("RecommentTask---end----- {}", sdf.format(new Date()));
    }
}
