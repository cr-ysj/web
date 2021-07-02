package com.example.demo.service.user;

import com.example.demo.pojo.db.user.User;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    User findByUserNameEnable(User reqUser);

    void saveUser(User reqUser);

    PageInfo getUserList(int page, int limit);
}
