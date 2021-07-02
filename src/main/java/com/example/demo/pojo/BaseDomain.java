package com.example.demo.pojo;

import com.example.demo.pojo.enums.DelStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseDomain implements Serializable {
    private DelStatus delStatus;
}
