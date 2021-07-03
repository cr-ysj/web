package com.example.demo.controller.user;

import com.example.demo.config.md5.MD5PasswordEncoder;
import com.example.demo.pojo.db.user.User;
import com.example.demo.pojo.enums.UserStatus;
import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@SuppressWarnings("all")
@Slf4j
@Controller
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private MD5PasswordEncoder   md5PasswordEncoder;

    //注册
    @ResponseBody
    @RequestMapping("/doRegister")
    public ResponseResult doRegister(@RequestBody User reqUser){
        //先判断数据库有木有一样的username
        User user=userService.findByUserNameEnable(reqUser);
        ResponseResult responseResult = new ResponseResult("500", "该名称已经被注册", null);
        if(user!=null){
            return responseResult;
        }
        //密码加密
        reqUser.setPassword(md5PasswordEncoder.encode(reqUser.getPassword()));
        //启用状态
        reqUser.setUserStatus(UserStatus.Enable);
        //保存到数据库 添加默认的角色guest
        //reqUser.setRoleList();
       try {
           userService.saveUser(reqUser);
           responseResult = new ResponseResult("200", "注册成功请重新登录", null);

       }
       catch (Exception e){
           log.error("注册失败:{}",e);
           responseResult = new ResponseResult("500", "注册失败请重试", null);

       }
        //返回前端
        return responseResult;
    }



    @ResponseBody
    @RequestMapping("/user/getUserList")
    public ResponseResult getUserList(@RequestParam("page") int page,
                           @RequestParam("limit") int limit){

        return  new ResponseResult("200","查询成功",userService.getUserList(page,limit));
    }


    @ResponseBody
    @RequestMapping("/user/grantRole")
    public ResponseResult grantRole(@RequestBody Map map){
        try {
            userService.grantRole(map);
            return new ResponseResult("200", "授权成功", null);

        }
        catch (Exception e){
            log.error("授权失败:{}",e);
            return new ResponseResult("500", "授权失败请重试", null);
        }
    }
    @ResponseBody
    @RequestMapping("/user/start")
    public ResponseResult start(Integer id){
        try {
            userService.start(id);
            return new ResponseResult("200", "启用成功", null);

        }
        catch (Exception e){
            log.error("启用失败:{}",e);
            return new ResponseResult("500", "启用失败请重试", null);
        }
    }

    @ResponseBody
    @RequestMapping("/user/stop")
    public ResponseResult stop(Integer id){
        try {
            userService.stop(id);
            return new ResponseResult("200", "停用成功", null);

        }
        catch (Exception e){
            log.error("授权失败:{}",e);
            return new ResponseResult("500", "停用失败请重试", null);
        }
    }
}
