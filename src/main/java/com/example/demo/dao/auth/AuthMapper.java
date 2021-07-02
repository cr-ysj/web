package com.example.demo.dao.auth;

import com.example.demo.pojo.db.auth.Auth;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthMapper {
    List<Auth> getAuthList();
}
