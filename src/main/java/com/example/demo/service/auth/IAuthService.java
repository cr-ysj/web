package com.example.demo.service.auth;

import com.github.pagehelper.PageInfo;

public interface IAuthService {
    PageInfo getAuthList(int page, int limit);
}
