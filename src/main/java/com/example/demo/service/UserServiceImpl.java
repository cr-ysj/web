package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.pojo.Auth;
import com.example.demo.pojo.Role;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


}
