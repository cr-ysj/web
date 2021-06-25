package com.example.demo.config.security;

import com.example.demo.config.md5.MD5PasswordEncoder;
import com.example.demo.config.security.filter.JWTFilter;
import com.example.demo.config.security.handler.AuthAccessDeniedHandler;
import com.example.demo.config.security.handler.LoginFailureHandler;
import com.example.demo.config.security.handler.LoginSuccessHandler;
import com.example.demo.pojo.constant.GlobalConstant;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;


@SuppressWarnings("all")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    //用户服务
    @Autowired
    private IUserService userService;

    //MD5加密组件
    @Autowired
    private  MD5PasswordEncoder md5PasswordEncoder;

    //登录实现方式
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(md5PasswordEncoder);
        return authenticationProvider;
    }
    //授权验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    //配置忽略掉的 URL 地址，一般对于静态文件
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(GlobalConstant.ignorings);
    }

    //跳转到登录界面
    @Bean
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint(String loginFormUrl){
        return  new   LoginUrlAuthenticationEntryPoint(loginFormUrl) {
            @Override
            protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
                return this.getLoginFormUrl();
            }
        };
    }
    /**
     * 注册bean sessionRegistry
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    //无权限处理器
    @Autowired AuthAccessDeniedHandler authAccessDeniedHandler;

    //登录处理过滤器
    @Autowired
    private AbstractAuthenticationProcessingFilter authenticationFilter;

    //登录成功处理器
    @Autowired LoginSuccessHandler loginSuccessHandler;

    //登录失败处理器
    @Autowired LoginFailureHandler loginFailureHandler;

    //header设置，支持跨域和ajax请求
    private StaticHeadersWriter headersWriter= new StaticHeadersWriter(Arrays.asList(
            new org.springframework.security.web.header.Header("Access-control-Allow-Origin","*"),
            new org.springframework.security.web.header.Header("Access-Control-Allow-Headers","Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With"),
            new org.springframework.security.web.header.Header("Access-Control-Allow-Credentials","true"),
            new org.springframework.security.web.header.Header("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH")

    ));


    //权限管理器
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

  @Autowired
  private JWTFilter jwtFilter;

    //security配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表示该页面可以在相同域名页面的 frame 中展示
        http.headers().frameOptions().sameOrigin();
        //忽略的url
        String[] ignoringUrls = GlobalConstant.ignoringUrls;
        ArrayList<String> matchUrl =new ArrayList();
        matchUrl.addAll( Arrays.asList(ignoringUrls));

        /**
         * ：Spring Security对url的权限判断有两种方式，一种是请求是permitAll的则直接返回校验通过，
         * 另外一个是判断Authentication是不是AnonymousAuthenticationToken，因为正常登录等产生的不是这个对象，如果不是这个类型的对象则表示登录成功了
         * isAnonymous中就是判断传递过来的Authentication对象是不是AnonymousAuthenticationToken，
         * 如果是AnonymousAuthenticationToken则表示没有登录，
         * 因为登录之后生成的对象是UsernamePasswordAuthenticationToken或其他Authentication对象，
         * */
        //SessionManagementFilter  this.trustResolver.isAnonymous(authentication)
        http.anonymous().and()
                //权限不足处理
                .exceptionHandling().accessDeniedHandler(authAccessDeniedHandler).and()
                .csrf().disable()//关闭csrf
                .httpBasic().and()
                .exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint(GlobalConstant.loginUrl))//登录页面
                .and()
                .authorizeRequests()
                .antMatchers(matchUrl.toArray(new String[matchUrl.size()])).permitAll()//忽略的url
                .anyRequest().authenticated() // 任何请求都必须经过身份验证
                .and()
                .cors()  //支持跨域
                .and()   //添加header设置，支持跨域和ajax请求
                .headers().addHeaderWriter(headersWriter)
                .and()
                .formLogin()
                .loginPage(GlobalConstant.loginUrl)//登录页面
                //登录验证(过滤器按照一定顺序加入过滤器链。)
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        authenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
    }


}
