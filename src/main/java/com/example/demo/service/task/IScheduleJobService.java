package com.example.demo.service.task;

import com.example.demo.pojo.page.PageRequest;
import com.example.demo.pojo.quartz.ScheduleJob;
import com.example.demo.pojo.response.ResponseResult;
import com.github.pagehelper.PageInfo;

public interface IScheduleJobService {
    //初始化任务调度
    void initSchedule();

    PageInfo getTaskList(PageRequest pageRequest);

    void saveTask(ScheduleJob job);

    void editTask(ScheduleJob job)  throws Exception;

    ResponseResult delTask(Long id, String type);

    void start(Long id) throws Exception;

    void stop(Long id) throws Exception;
}
