package com.example.demo.service.resource.impl;

import com.example.demo.dao.resource.ResourceMapper;
import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.db.resource.Resource;
import com.example.demo.service.resource.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@SuppressWarnings("all")
@Service
public class ResourceServiceImpl implements IResourceService {


    @Autowired
    private ResourceMapper resourceMapper;

    //获取权限资源
    @Override
    public Map<String, List<String>> getAuthAndResource() {
        Map<String, List<String>> result =new HashMap<>();
        //查询资源
        List<Resource> resources = resourceMapper.getAllResources();
        HashSet<String> set=new HashSet();
        resources.stream().forEach(resource -> {
            set.add(resource.getPath());
        });
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String path = iterator.next();
            List<String> auths=new ArrayList<>();
            resources.stream().forEach(resource -> {
                if((resource.getPath()).equals(path)){
                    List<Auth> authList = resource.getAuthList();
                    authList.forEach(auth -> {
                        auths.add(auth.getAuth());
                    });
                }
            });
            result.put(path,auths);
        }
        return result;
    }
}
