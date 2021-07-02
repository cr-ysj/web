package com.example.demo.pojo.enums;

import lombok.Getter;

@Getter
public enum UserStatus implements BaseEnum {

    Enable(1,"启用"),
    Disable(2,"停用");


    private  UserStatus(int code,String msg){
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
