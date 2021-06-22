package com.example.demo.pojo;

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Role  extends BaseDomain implements Serializable  {

    private static final long serialVersionUID = 1L;


    private Long id;

    private String roleName;

    private String description;


    private List<Auth> authList=new ArrayList<>();

    private List<Long> userIds;

    private List<Long> authIds;

}
