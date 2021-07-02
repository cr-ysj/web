package com.example.demo.service.task.impl;

import com.example.demo.config.quartz.QuartzManager;
import com.example.demo.dao.task.ScheduleJobMapper;
import com.example.demo.pojo.quartz.ScheduleJob;
import com.example.demo.service.task.IScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings("all")
public class ScheduleJobServiceImpl  implements IScheduleJobService {
    @Autowired
    private QuartzManager quartzManager;

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Override
    public void initSchedule() {
        log.info("初始化定时任务");
        // 这里获取任务信息数据
        List<ScheduleJob> jobList = scheduleJobMapper.findInitScheduleJobs();
        quartzManager.addJobs(jobList);
    }
}
