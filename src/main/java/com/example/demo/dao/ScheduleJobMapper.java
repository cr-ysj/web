package com.example.demo.dao;


import com.example.demo.pojo.quartz.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
    List<ScheduleJob> findInitScheduleJobs();
}
