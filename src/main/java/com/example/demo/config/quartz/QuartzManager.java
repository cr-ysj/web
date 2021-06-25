package com.example.demo.config.quartz;

import com.example.demo.pojo.enums.JobStatus;
import com.example.demo.pojo.quartz.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@SuppressWarnings("all")
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    //添加定时任务
    public void addJobs(List<ScheduleJob> jobList) {
         for (ScheduleJob task : jobList) {
            if (JobStatus.START.equals(task.getJobStatus())) {
                try {
                    // 创建jobDetail实例，绑定Job实现类 指明job的名称，所在组的名称，以及绑定job类
                    Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(task.getBeanClass()).newInstance().getClass());
                    JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(task.getJobName(), task.getJobGroup()).build();// 任务名称和组构成任务key
                    // 定义调度触发规则 使用cornTrigger规则
                    Trigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getJobName(), task.getJobGroup())// 触发器key
                            .startAt(DateBuilder.futureDate(0, DateBuilder.IntervalUnit.SECOND)) //立即启动
                            .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression())).startNow().build();
                    // 把作业和触发器注册到任务调度中
                    scheduler.scheduleJob(jobDetail, trigger);
                    // 启动
                    if (!scheduler.isShutdown()) {
                        scheduler.start();
                        log.error("任务初始化启动成功:{}",task.getJobName());
                    }
                } catch (Exception e) {
                    log.error("任务初始化启动失败任务名称:{},原因:{}",task.getJobName(),e.getMessage());
                }
            }
        }
    }
}
