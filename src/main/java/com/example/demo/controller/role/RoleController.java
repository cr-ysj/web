package com.example.demo.controller.role;

import com.example.demo.pojo.db.role.Role;
import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.role.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class RoleController {


    @Autowired
    private IRoleService roleService;

    @ResponseBody
    @RequestMapping("/role/getRoleList")
    public ResponseResult getRoleList(@RequestParam("page") int page,
                                      @RequestParam("limit") int limit){

        return  new ResponseResult("200","查询成功",roleService.getRoleList(page,limit));
    }

    @ResponseBody
    @RequestMapping("/role/saveRole")
    public ResponseResult saveRole(@RequestBody Role role){
        try {
            roleService.saveRole(role);
           return new ResponseResult("200","保存成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
           return  new ResponseResult("500","保存失败",null);
        }
    }

    @ResponseBody
    @RequestMapping("/role/delRoles")
    public ResponseResult saveRole(@RequestBody Map params){
        try {
            roleService.delRoles(params);
            return new ResponseResult("200","删除成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","删除失败",null);
        }
    }
    @ResponseBody
    @RequestMapping("/role/startRoles")
    public ResponseResult startRoles(@RequestParam("ids") List list){
        try {
            roleService.startRoles(list);
            return new ResponseResult("200","启用成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","启用失败",null);
        }
    }
    @ResponseBody
    @RequestMapping("/role/editRole")
    public ResponseResult editRole(@RequestBody Role role){
        try {
            roleService.editRole(role);
            return new ResponseResult("200","修改成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","修改失败",null);
        }
    }

}
