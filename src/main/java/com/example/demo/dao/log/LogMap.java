package com.example.demo.dao.log;

import com.example.demo.pojo.db.log.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogMap {
    void saveLog(SysLog sysLog);

    List<SysLog> getLogList(Map queryParams);
}
