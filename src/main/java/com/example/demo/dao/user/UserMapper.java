package com.example.demo.dao.user;

import com.example.demo.pojo.db.user.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserByUsername(User user);

    User findByUserNameEnable(User user);

    void saveUser(User user);

    List<User> getUserList();

    void grantRole(Map map);

    void start(Integer id);

    void stop(Integer id);

    void removeRoleByUserId(Integer id);
}
