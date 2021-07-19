package com.example.demo.service.log;

import com.example.demo.pojo.db.log.SysLog;
import com.example.demo.pojo.page.PageRequest;
import com.github.pagehelper.PageInfo;

public interface ISystemLogService {
    void saveLog(SysLog sysLog);

    PageInfo getLogList(PageRequest pageRequest);
}
