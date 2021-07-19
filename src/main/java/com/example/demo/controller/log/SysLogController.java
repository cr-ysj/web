package com.example.demo.controller.log;

import com.example.demo.pojo.page.PageRequest;
import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.log.ISystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class SysLogController {


    @Autowired
    private ISystemLogService systemLogService;

    @ResponseBody
    @RequestMapping("/log/getLogList")
    public ResponseResult getLogList( PageRequest pageRequest){
        return  new ResponseResult("200","查询成功",systemLogService.getLogList(pageRequest));
    }
}
