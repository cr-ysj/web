package com.example.demo.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Resource  extends BaseDomain implements Serializable {


    private Long id;

    // 资源名称
    private String resourceName;

    // 资源位置
    private String path;

    // 描述
    private String description;

    //权限
    private List<Auth> authList=new ArrayList<>();

    private List<Long> authIds;
}
