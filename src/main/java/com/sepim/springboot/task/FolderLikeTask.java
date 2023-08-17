package com.sepim.springboot.task;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.FolderLikeService;
import com.sepim.springboot.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author: SorrowPhage
 * @date: 2023/8/4
 */
@RequiredArgsConstructor
@Slf4j
public class FolderLikeTask extends QuartzJobBean {

    private final FolderLikeService folderLikeService;

    private final RedisService redisService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info(String.valueOf(LocalDateTimeUtil.now()));
        log.info("=======Redis数据迁移=======");
        ResultData ping = redisService.ping();
        if (!"400".equals(ping.getFlag())) {
            folderLikeService.transLikedFromRedis2DB();
            folderLikeService.transLikedCountFromRedis2DB();
        }
    }

}
