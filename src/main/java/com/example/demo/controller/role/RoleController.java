package com.example.demo.controller.role;

import com.example.demo.pojo.db.role.Role;
import com.example.demo.pojo.enums.OptionLog;
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

import static com.example.demo.pojo.constant.GlobalConstant.*;

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

    @OptionLog(
            optionModel=optionModel_Role,
            optionType= optionType_Save,
            optionDesc="添加角色")
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


    @OptionLog(
            optionModel=optionModel_Role,
            optionType= optionType_Save,
            optionDesc="删除角色")
    @ResponseBody
    @RequestMapping("/role/delRoles")
    public ResponseResult saveRole(@RequestBody Map params){
        try {
            roleService.delRoles(params);
            if("del".equals(params.get("type"))){
                return new ResponseResult("200","删除成功",null);
            }
            else {
                return new ResponseResult("200","禁用成功",null);

            }
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","删除失败",null);
        }
    }

    @OptionLog(
            optionModel=optionModel_Role,
            optionType= optionType_Edit,
            optionDesc="启用角色")
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

    @OptionLog(
            optionModel=optionModel_Role,
            optionType= optionType_Edit,
            optionDesc="编辑角色")
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
