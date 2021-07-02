package com.example.demo.pojo.enums;

import lombok.Getter;

@Getter
public enum DelStatus implements BaseEnum {

    USE(1,"正常"),
    DEL(2,"删除");

    private  DelStatus(int code,String msg){
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
