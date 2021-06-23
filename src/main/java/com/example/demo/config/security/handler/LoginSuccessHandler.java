package com.example.demo.config.security.handler;

import cn.hutool.json.JSONUtil;
import com.example.demo.config.security.filter.JWTFilter;
import com.example.demo.pojo.constant.GlobalConstant;
import com.example.demo.utils.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 登录成功组件
 * */
@SuppressWarnings("all")
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private  JWTFilter jwtFilter;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper=new ObjectMapper();
        // 添加一个map对象，方便等下转换成字符串
        Map json = new HashMap<String ,Object>();
        //todo 创建令牌存入到redis中
        //生成uuid
        String key= UUID.randomUUID().toString().replaceAll("-","").trim();
        //存入redis中(设置过期时间) 数据结构hash结构   key -token   auth-auth
        String jwt = jwtFilter.createJWT(authentication.getName(), GlobalConstant.tokenValidityInMilliseconds);
        redisUtils.setHash(key,GlobalConstant.jwt,jwt,GlobalConstant.TokenExpireTime);
        String principal = JSONUtil.toJsonStr(authentication.getPrincipal());
        redisUtils.setHash(key,GlobalConstant.auths, principal,GlobalConstant.TokenExpireTime);
        json.put(GlobalConstant.jwt,key);
        json.put("code",GlobalConstant.successCode);
        PrintWriter out = resp.getWriter();
        out.write(mapper.writeValueAsString(json));
        out.println();
        out.flush();
        out.close();
    }
}
