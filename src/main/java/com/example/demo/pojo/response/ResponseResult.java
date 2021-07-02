package com.example.demo.pojo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable {

    private String code ;

    private String message;

    private T data;

    public ResponseResult(){

    }

    public ResponseResult(String code ,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

}
