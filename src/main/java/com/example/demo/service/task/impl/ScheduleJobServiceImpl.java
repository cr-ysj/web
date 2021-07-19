package com.example.demo.service.task.impl;

import com.example.demo.config.quartz.QuartzManager;
import com.example.demo.dao.task.ScheduleJobMapper;
import com.example.demo.pojo.db.log.SysLog;
import com.example.demo.pojo.db.resource.Resource;
import com.example.demo.pojo.enums.JobStatus;
import com.example.demo.pojo.page.PageRequest;
import com.example.demo.pojo.quartz.ScheduleJob;
import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.task.IScheduleJobService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings("all")
public class ScheduleJobServiceImpl  implements IScheduleJobService {
    @Autowired
    private QuartzManager quartzManager;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Override
    public void initSchedule() {
        log.info("初始化定时任务");
        // 这里获取任务信息数据
        List<ScheduleJob> jobList = scheduleJobMapper.findInitScheduleJobs();
        quartzManager.addJobs(jobList);
    }




    @Override
    public PageInfo getTaskList(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getLimit());
        List<ScheduleJob> list= scheduleJobMapper.getTaskList(pageRequest.getQueryParams());
        PageInfo<Resource> pageBean=new PageInfo(list);
        return pageBean;
    }

    @Transactional
    @Override
    public void saveTask(ScheduleJob job) {
        scheduleJobMapper.saveTask(job);
    }

    @Override
    public void editTask(ScheduleJob job) throws Exception{
        ScheduleJob oldJob=scheduleJobMapper.getTaskById(job.getId());

        if(JobStatus.START.equals(oldJob.getJobStatus())&&(!job.getCronExpression().equals(oldJob.getCronExpression()))){
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);
        }
        scheduleJobMapper.editTask(job);
    }

    @Override
    public ResponseResult delTask(Long id, String type) {
        ScheduleJob job=scheduleJobMapper.getTaskById(id);
        if(JobStatus.START.equals(job.getJobStatus())){
            return  new ResponseResult("500","删除失败,请先停止任务",null);
        }
        if("del".equals(type)){
            scheduleJobMapper.delTask(id);
            return new ResponseResult("200","删除成功",null);
        }
        else{
            scheduleJobMapper.delTaskForever(id);
            return new ResponseResult("200","永久删除成功",null);
        }
    }

    @Override
    public void start(Long id) throws Exception{
        ScheduleJob job=scheduleJobMapper.getTaskById(id);
        if(JobStatus.START.equals(job.getJobStatus())){
            return;
        }
        // 创建jobDetail实例，绑定Job实现类
        // 指明job的名称，所在组的名称，以及绑定job类
        Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(job.getBeanClass()).newInstance().getClass());
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobName(), job.getJobGroup()).build();// 任务名称和组构成任务key
        // 定义调度触发规则
        // 使用cornTrigger规则
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())// 触发器key
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression())).startNow().build();
        // 把作业和触发器注册到任务调度中
        scheduler.scheduleJob(jobDetail, trigger);
        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
            scheduleJobMapper.start(id);
        }

    }

    @Override
    public void stop(Long id) throws Exception{
        ScheduleJob job=scheduleJobMapper.getTaskById(id);
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.pauseJob(jobKey);
        scheduleJobMapper.stop(id);
    }
}
