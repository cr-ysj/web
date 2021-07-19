package com.example.demo.controller.task;

import com.example.demo.pojo.page.PageRequest;
import com.example.demo.pojo.quartz.ScheduleJob;
import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.log.ISystemLogService;
import com.example.demo.service.task.IScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class TaskController {

    @Autowired
    private IScheduleJobService scheduleJobService;

    @ResponseBody
    @RequestMapping("/task/getTaskList")
    public ResponseResult getTaskList(PageRequest pageRequest){
        return  new ResponseResult("200","查询成功",scheduleJobService.getTaskList(pageRequest));
    }

    @ResponseBody
    @RequestMapping("/task/saveTask")
    public ResponseResult saveTask(@RequestBody  ScheduleJob job){

        try {
            scheduleJobService.saveTask(job);
            return  new ResponseResult("200","保存成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","保存失败",null);
        }
    }
    @ResponseBody
    @RequestMapping("/task/editTask")
    public ResponseResult editTask(@RequestBody  ScheduleJob job){
        try {
            scheduleJobService.editTask(job);
            return  new ResponseResult("200","修改成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","修改失败",null);
        }
    }
    @ResponseBody
    @RequestMapping("/task/delTask")
    public ResponseResult delTask(@RequestParam("id") Long id,@RequestParam("type") String type){
        try {
            return scheduleJobService.delTask(id,type);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","删除失败",null);
        }
    }

    @ResponseBody
    @RequestMapping("/task/start")
    public ResponseResult start(@RequestParam("id") Long id){
        try {
             scheduleJobService.start(id);
            return  new ResponseResult("200","启动成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","启动失败",null);
        }
    }

    @ResponseBody
    @RequestMapping("/task/stop")
    public ResponseResult stop(@RequestParam("id") Long id){
        try {
            scheduleJobService.stop(id);
            return  new ResponseResult("200","停止成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","停止失败",null);
        }
    }
}
