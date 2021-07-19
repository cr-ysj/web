package com.example.demo.service.resource.impl;

import com.example.demo.dao.resource.ResourceMapper;
import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.db.resource.Resource;
import com.example.demo.pojo.enums.OptionLog;
import com.example.demo.service.resource.IResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import static com.example.demo.pojo.constant.GlobalConstant.optionModel_Resource;
import static com.example.demo.pojo.constant.GlobalConstant.optionType_View;

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


    @Override
    public PageInfo getResourceList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);//改写语句实现分页查询
        List<Resource> list= resourceMapper.getResourceList();
        PageInfo<Resource> pageBean=new PageInfo(list);
        return pageBean;
    }

    @Transactional
    @Override
    public void saveResource(Resource resource) {
        resourceMapper.saveResource(resource);
    }

    @Transactional
    @Override
    public void delReources(List list) {
        //删除资源
        resourceMapper.deleteResources(list);
        //删除资源和权限关联
        resourceMapper.removeAuthAndResourceByResourceIds(list);
    }

    @Transactional
    @Override
    public void editResource(Resource resource) {
        resourceMapper.editResource(resource);
    }


}
