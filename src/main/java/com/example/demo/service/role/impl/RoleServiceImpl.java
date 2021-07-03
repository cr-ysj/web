package com.example.demo.service.role.impl;

import com.example.demo.dao.role.RoleMapper;
import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.db.role.Role;
import com.example.demo.pojo.db.user.User;
import com.example.demo.service.role.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service
public class RoleServiceImpl implements IRoleService {


    @Autowired
    private RoleMapper roleMapper;



    @Override
    public PageInfo getRoleList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);//改写语句实现分页查询
        List<Role> list= roleMapper.getRoleList();
        PageInfo<Role> pageBean=new PageInfo<>(list);
        return pageBean;
    }

    @Transactional
    @Override
    public void saveRole(Role role) {
        roleMapper.saveRole(role);
        List<Auth> authList = role.getAuthList();
        for (int i = 0; i < authList.size(); i++) {
            authList.get(i).setRoleId(role.getId());
        }
        this.roleMapper.saveRoleAndAuths(authList);
    }
    @Transactional
    @Override
    public void delRoles(Map params) {
        List roids=(ArrayList)params.get("ids");
        if("del".equals(params.get("type"))){
            //todo 需要解除用户和角色的绑定
            roleMapper.removeRoleByRoleIds(roids);
            roleMapper.delByRoleIds(roids);
            roleMapper.removeAuthByRoleIds(roids);
        }
        if("stop".equals(params.get("type"))){
            roleMapper.stopByRoleIds(roids);
        }
    }

    @Transactional
    @Override
    public void startRoles(List list) {
        roleMapper.startRolesByRoleId(list);
    }

    @Transactional
    @Override
    public void editRole(Role role) {
        //删除权限和角色的关联
        roleMapper.removeAuthByRoleId(role);
        //修改
        roleMapper.updateRole(role);
        //保存权限和角色
        List<Auth> authList = role.getAuthList();
        for (int i = 0; i < authList.size(); i++) {
            authList.get(i).setRoleId(role.getId());
        }
        this.roleMapper.saveRoleAndAuths(authList);
    }
}
