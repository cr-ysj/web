package com.example.demo.service.role;

import com.example.demo.pojo.db.role.Role;
import com.github.pagehelper.PageInfo;

public interface IRoleService {
    PageInfo getRoleList(int page, int limit);

    void saveRole(Role role);
}
