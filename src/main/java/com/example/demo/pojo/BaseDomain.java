package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseDomain implements Serializable {

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;


}
