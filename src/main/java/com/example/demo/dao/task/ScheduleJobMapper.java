package com.example.demo.dao.task;


import com.example.demo.pojo.quartz.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
    List<ScheduleJob> findInitScheduleJobs();

    List<ScheduleJob> getTaskList(Map queryParams);

    void saveTask(ScheduleJob job);

    void editTask(ScheduleJob job);

    ScheduleJob getTaskById(Long id);

    void delTask(Long id);

    void start(Long id);

    void stop(Long id);

    void delTaskForever(Long id);
}
