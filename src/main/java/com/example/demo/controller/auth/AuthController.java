package com.example.demo.controller.auth;

import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.auth.IAuthService;
import com.example.demo.service.role.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class AuthController {
    @Autowired
    private IAuthService authService;

    @ResponseBody
    @RequestMapping("/auth/getAuthList")
    public ResponseResult getAuthList(@RequestParam("page") int page,
                                      @RequestParam("limit") int limit){

        return  new ResponseResult("200","查询成功",authService.getAuthList(page,limit));
    }

    @ResponseBody
    @RequestMapping("/auth/saveAuth")
    public ResponseResult saveAuth(@RequestBody Auth auth){
        try {
            authService.saveAuth(auth);
            return new ResponseResult("200","保存成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","保存失败",null);
        }
    }

    @ResponseBody
    @RequestMapping("/auth/delAuths")
    public ResponseResult delAuths(@RequestParam("ids") List list){
        try {
            authService.delAuths(list);
            return new ResponseResult("200","删除成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","删除失败",null);
        }
    }
    @ResponseBody
    @RequestMapping("/auth/editAuth")
    public ResponseResult editAuth(@RequestBody Auth auth){
        try {
            authService.editAuth(auth);
            return new ResponseResult("200","修改成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseResult("500","修改失败",null);
        }
    }

}
