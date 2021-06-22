package com.example.demo.config.security.handler;

import com.example.demo.pojo.constant.GlobalConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 无权限的处理
 * */
@Component
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    //无权限处理逻辑
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper=new ObjectMapper();
        Map json = new HashMap<String ,Object>();
        json.put("code", GlobalConstant.noAuthCode);
        json.put("msg","无权限");
        PrintWriter out = resp.getWriter();
        out.write(mapper.writeValueAsString(json));
        out.println();
        out.flush();
        out.close();
    }
}