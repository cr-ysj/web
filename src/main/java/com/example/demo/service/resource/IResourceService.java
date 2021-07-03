package com.example.demo.service.resource;

import com.example.demo.pojo.db.resource.Resource;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IResourceService {
    Map<String, List<String>> getAuthAndResource();

    PageInfo getResourceList(int page, int limit);

    void saveResource(Resource resource);

    void delReources(List list);

    void editResource(Resource resource);
}
