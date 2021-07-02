package com.example.demo.pojo.db.role;

import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(value = {"handler"})
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
