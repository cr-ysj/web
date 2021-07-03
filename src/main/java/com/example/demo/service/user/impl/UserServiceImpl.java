package com.example.demo.service.user.impl;

import com.example.demo.dao.user.UserMapper;
import com.example.demo.pojo.db.user.User;
import com.example.demo.service.user.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("all")
public class UserServiceImpl implements IUserService {


    @Autowired
    UserMapper userMapper;


    //登录实现方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询用户
        User user = userMapper.getUserByUsername(new User(username));
        if (user == null) {
            //这里找不到必须抛异常
            throw new UsernameNotFoundException("User " + username + " was not found in db");
        }
        // 2. 设置权限
        Collection<GrantedAuthority> grantedAuthorities =user.getAuthorities();
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
    }


    @Override
    public User findByUserNameEnable(User reqUser) {
        return userMapper.findByUserNameEnable(reqUser);
    }

    @Transactional
    @Override
    public void saveUser(User reqUser) {
        userMapper.saveUser(reqUser);
    }

    @Override
    public PageInfo getUserList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);//改写语句实现分页查询
        List<User> list= userMapper.getUserList();
        PageInfo<User> pageBean=new PageInfo<>(list);
        return pageBean;
    }

    @Transactional
    @Override
    public void grantRole(Map map) {
        userMapper.removeRoleByUserId(Integer.valueOf(map.get("userId").toString()));
        userMapper.grantRole(map);
    }

    @Transactional
    @Override
    public void start(Integer id) {
        userMapper.start(id);
    }

    @Transactional
    @Override
    public void stop(Integer id) {
        userMapper.stop(id);
    }
}
