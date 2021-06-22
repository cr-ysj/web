package com.example.demo.config.security.handler;

import com.example.demo.pojo.constant.GlobalConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录失败组件
 * */
@Component
public class LoginFailureHandler  extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper=new ObjectMapper();
        // 添加一个map对象，方便等下转换成字符串
        Map json = new HashMap<String ,Object>();
        json.put("code", GlobalConstant.failCode);
        PrintWriter out = resp.getWriter();
        out.write(mapper.writeValueAsString(json));
        out.flush();
        out.close();
    }
}
