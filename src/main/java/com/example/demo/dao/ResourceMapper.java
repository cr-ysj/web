package com.example.demo.dao;

import com.example.demo.pojo.Resource;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface ResourceMapper  extends BaseMapper<Resource> {

    List<Resource> getAllResources();
}
