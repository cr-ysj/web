package com.example.demo.jobdetail;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import java.util.Date;

@Slf4j
@DisallowConcurrentExecution //作业不并发
public class HelloWold extends ZkQuartzJobBean {

    @Override
    void run(JobExecutionContext jobexecutioncontext) {
        log.info("start task:{}",DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SSSSSS"));
    }
}

