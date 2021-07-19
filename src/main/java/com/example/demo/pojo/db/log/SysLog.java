package com.example.demo.pojo.db.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志表
 * */
@Data
public class SysLog implements Serializable {

    private Long id;
   //操作模块
    private String optionModel;
    //操作类型
    private String optionType;
     //操作描述
    private String optionDesc;
     //操作员
    private String optionUser;
   //操作方法
    private String optionFunc;
     //请求url
    private String requestUrl;
     //请求ip
    private String requestIp;
    //操作时间
    private Date optionTime;
   //请求参数
    private String requestParams;
     //返回结果
    private String responseResult ;
     //请求ip
    private Boolean isOptionError;
    //错误日志
    private String errorMsg;
}


