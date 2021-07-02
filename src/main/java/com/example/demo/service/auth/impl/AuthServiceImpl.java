package com.example.demo.service.auth.impl;

import com.example.demo.dao.auth.AuthMapper;
import com.example.demo.dao.role.RoleMapper;
import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.db.user.User;
import com.example.demo.service.auth.IAuthService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
