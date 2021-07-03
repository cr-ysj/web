package com.example.demo.service.role;

import com.example.demo.pojo.db.role.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    PageInfo getRoleList(int page, int limit);

    void saveRole(Role role);

    void delRoles(Map params);

    void startRoles(List list);

    void editRole(Role role);
}
