package com.example.demo.controller.auth;

import com.example.demo.pojo.response.ResponseResult;
import com.example.demo.service.auth.IAuthService;
import com.example.demo.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
