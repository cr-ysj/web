package com.example.demo.jobdetail;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import java.io.*;
import java.util.Date;

@Slf4j
@DisallowConcurrentExecution //作业不并发
public class HelloWold extends ZkQuartzJobBean {

    @Override
    void run(JobExecutionContext jobexecutioncontext) {
        //写文件
        try {
            File file=new File("C:\\Users\\Administrator\\Desktop\\quartz.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss").getBytes());
            fileOutputStream.write(System.getProperty("line.separator").getBytes());
            log.info("start task:{}",DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

