package com.example.demo.service.log.impl;

import com.example.demo.dao.log.LogMap;
import com.example.demo.pojo.db.log.SysLog;
import com.example.demo.pojo.db.resource.Resource;
import com.example.demo.pojo.page.PageRequest;
import com.example.demo.service.log.ISystemLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("all")
@Slf4j
@Service
public class SystemLogServiceImpl implements ISystemLogService {

    @Autowired
    private LogMap logMap;

    @Transactional
    @Override
    public void saveLog(SysLog sysLog) {
        logMap.saveLog(sysLog);
    }

    @Override
    public PageInfo getLogList(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getLimit());
        List<SysLog> list= logMap.getLogList(pageRequest.getQueryParams());
        PageInfo<Resource> pageBean=new PageInfo(list);
        return pageBean;
    }
}
