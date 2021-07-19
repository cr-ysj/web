package com.example.demo.controller.user;

import com.example.demo.config.md5.MD5PasswordEncoder;
import com.example.demo.pojo.constant.GlobalConstant;
import com.example.demo.pojo.db.user.User;
import com.example.demo.pojo.enums.OptionLog;
import com.example.demo.pojo.enums.UserStatus;
import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.user.IUserService;
import com.example.demo.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.example.demo.pojo.constant.GlobalConstant.*;

@SuppressWarnings("all")
@Slf4j
@Controller
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private MD5PasswordEncoder   md5PasswordEncoder;

    @Autowired
    private RedisUtils redisUtils;
    //注册
    @ResponseBody
    @RequestMapping("/loginOut")
    public ResponseResult loginOut(HttpServletRequest request, HttpServletResponse response){
        String jwt=request.getHeader(GlobalConstant.jwt);
        redisUtils.removeKey(key);
        ResponseResult responseResult = new ResponseResult("200", "退出成功", null);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {//清除认证
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return responseResult;
    }

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

    @OptionLog(
            optionModel=optionModel_User,
            optionType= optionType_Edit,
            optionDesc="授权")
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

    @OptionLog(
            optionModel=optionModel_User,
            optionType= optionType_Edit,
            optionDesc="启用")
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

    @OptionLog(
            optionModel=optionModel_User,
            optionType= optionType_Edit,
            optionDesc="停用")
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
