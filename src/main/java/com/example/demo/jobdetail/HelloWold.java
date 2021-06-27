package com.example.demo.jobdetail;

import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import java.util.Date;

@DisallowConcurrentExecution //作业不并发
public class HelloWold extends ZkQuartzJobBean {

    @Override
    void run(JobExecutionContext jobexecutioncontext) {
        System.out.println("hello");
    }
}

