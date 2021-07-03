package com.example.demo.service.auth.impl;

import com.example.demo.dao.auth.AuthMapper;
import com.example.demo.dao.role.RoleMapper;
import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.db.resource.Resource;
import com.example.demo.pojo.db.user.User;
import com.example.demo.service.auth.IAuthService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("all")
@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public PageInfo getAuthList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);//改写语句实现分页查询
        List<Auth> list= authMapper.getAuthList();
        PageInfo<Auth> pageBean=new PageInfo(list);
        return pageBean;
    }

    @Transactional
    @Override
    public void saveAuth(Auth auth) {
        authMapper.saveAuth(auth);
        List<Resource> resources = auth.getResources();
        for (int i = 0; i < resources.size(); i++) {
            resources.get(i).setAuthId(auth.getId());
        }
        authMapper.saveAuthAndResources(resources);
    }

    @Transactional
    @Override
    public void delAuths(List list) {
        //删除权限auth
        authMapper.delAuths(list);
        //解除权限和角色绑定
        authMapper.removeRoleAndAuthByAuthIds(list);
        //解除权限和资源绑定
        authMapper.removeAuthAndResourceByAuthIds(list);
    }


    @Transactional
    @Override
    public void editAuth(Auth auth) {
        //修改权限
        authMapper.editAuth(auth);
        //解除权限和资源绑定
        authMapper.removeAuthAndResourceByAuthId(auth);
        //绑定权限和资源
        List<Resource> resources = auth.getResources();
        for (int i = 0; i < resources.size(); i++) {
            resources.get(i).setAuthId(auth.getId());
        }
        authMapper.saveAuthAndResources(resources);

    }


}
