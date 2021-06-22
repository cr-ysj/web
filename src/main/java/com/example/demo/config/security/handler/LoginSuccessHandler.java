package com.example.demo.config.security.handler;

import com.example.demo.config.security.filter.JWTFilter;
import com.example.demo.pojo.constant.GlobalConstant;
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


/**
 * 登录成功组件
 * */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired JWTFilter jwtFilter;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper=new ObjectMapper();
        // 添加一个map对象，方便等下转换成字符串
        Map json = new HashMap<String ,Object>();
        json.put(GlobalConstant.jwt,jwtFilter.createJWT(authentication, GlobalConstant.tokenValidityInMilliseconds));
        json.put("code",GlobalConstant.successCode);
        PrintWriter out = resp.getWriter();
        out.write(mapper.writeValueAsString(json));
        out.println();
        out.flush();
        out.close();
    }
}
