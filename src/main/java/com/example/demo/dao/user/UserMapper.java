package com.example.demo.dao.user;

import com.example.demo.pojo.db.user.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserByUsername(User user);

    User findByUserNameEnable(User user);

    void saveUser(User user);

    List<User> getUserList();
}
