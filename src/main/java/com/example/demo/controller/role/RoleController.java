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

}
