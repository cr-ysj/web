package com.example.demo.pojo.enums;

public enum FileType implements BaseEnum{

    PICTURE(1,"图片"),
    PACKAGE(2,"压缩包"),
    VIDEO(3,"视频"),
    AUDIO (4,"音频"),
    FILE (5,"文档");
    private  FileType(int code,String msg){
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
