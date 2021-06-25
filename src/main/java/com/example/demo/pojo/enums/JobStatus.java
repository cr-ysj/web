package com.example.demo.pojo.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum JobStatus implements BaseEnum {

    START(1,"启动"),
    STOP(2,"停止"),
    DEL(3,"删除");

    private  JobStatus(int code,String msg){
            this.code=code;
            this.msg=msg;
    }
    private int code ;
    private String msg;

    @Override
    public int getValue() {
        return this.code;
    }
}
