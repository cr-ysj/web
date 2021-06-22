package com.example.demo.config.security.filter;

import com.alibaba.fastjson.JSONObject;

import com.example.demo.pojo.constant.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("all")
@Component("UsernamePasswordAuthenticationFilter")
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Override
    @Autowired
    public void setAuthenticationManager( AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
    // 默认只支持 POST 请求
    private boolean postOnly = true;
    //  初始化一个用户密码 认证过滤器  默认的登录uri 是 /login 请求方式是POST
    public UsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher(GlobalConstant.doLoginUrl, GlobalConstant.httpMethod_POST));
    }

    // 实现其父类 AbstractAuthenticationProcessingFilter 提供的钩子方法 用去尝试认证
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 判断请求方式是否是POST
        if (postOnly && !request.getMethod().equals(GlobalConstant.httpMethod_POST)) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username =request.getParameter("username");
        String password =request.getParameter("password");
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim();
        password = password.trim();
        // 然后把账号名、密码封装到 一个认证Token对象中，这是就是一个通行证，但是这时的状态时不可信的，一旦通过认证就变为可信的
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // 然后 使用 父类中的 AuthenticationManager 对Token 进行认证
        return super.getAuthenticationManager().authenticate(authRequest);
    }

}