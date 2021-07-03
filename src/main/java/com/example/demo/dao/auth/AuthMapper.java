package com.example.demo.dao.auth;

import com.example.demo.pojo.db.auth.Auth;

import com.example.demo.pojo.db.resource.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthMapper {
    List<Auth> getAuthList();

    void saveAuth(Auth auth);

    void saveAuthAndResources(List<Resource> resources);

    void delAuths(List list);

    void removeRoleAndAuthByAuthIds(List list);

    void removeAuthAndResourceByAuthIds(List list);

    void editAuth(Auth auth);

    void removeAuthAndResourceByAuthId(Auth auth);
}
