package com.example.demo.dao.role;

import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.db.role.Role;
import com.example.demo.pojo.db.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> getRoleList();

    void saveRole(Role role);

    void saveRoleAndAuths(List<Auth> authList);

    void delByRoleIds(List roids);

    void removeAuthByRoleIds(List roids);

    void stopByRoleIds(List roids);

    void removeRoleByRoleIds(List roids);

    void startRolesByRoleId(List list);

    void updateRole(Role role);

    void removeAuthByRoleId(Role role);
}
