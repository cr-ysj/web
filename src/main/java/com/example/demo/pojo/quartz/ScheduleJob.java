package com.example.demo.pojo.quartz;

import com.example.demo.pojo.enums.JobStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class ScheduleJob   implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    // 任务名
    private String jobName;

    // 任务描述
    private String description;

    // cron表达式
    private String cronExpression;

    // 任务执行时调用哪个类的方法 包名+类名
    private String beanClass;

    // 任务状态
    private JobStatus jobStatus;

    // 任务分组
    private String jobGroup;



}
