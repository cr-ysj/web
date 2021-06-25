package com.example.demo.config.listener;

import com.example.demo.service.IScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * spring boot项目启动后执行
 * */
@Component
@Order(value = 1)
@SuppressWarnings("all")
public class ScheduleJobInitListener implements CommandLineRunner {
    @Autowired
    private IScheduleJobService scheduleJobService;
    @Override
    public void run(String... arg0) throws Exception {
        try {
            scheduleJobService.initSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}