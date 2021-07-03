package com.example.demo.service.auth;

import com.example.demo.pojo.db.auth.Auth;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IAuthService {
    PageInfo getAuthList(int page, int limit);

    void saveAuth(Auth auth);

    void delAuths(List list);

    void editAuth(Auth auth);
}
