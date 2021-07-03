package com.example.demo.dao.resource;

import com.example.demo.pojo.db.resource.Resource;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface ResourceMapper  extends BaseMapper<Resource> {

    List<Resource> getAllResources();

    List<Resource> getResourceList();

    void saveResource(Resource resource);

    void deleteResources(List list);

    void removeAuthAndResourceByResourceIds(List list);

    void editResource(Resource resource);
}
