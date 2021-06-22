package com.example.demo.config.security.filter;

import cn.hutool.core.date.DateUnit;
import cn.hutool.json.JSONUtil;
import com.example.demo.dao.UserMapper;
import com.example.demo.pojo.Auth;
import com.example.demo.pojo.Role;
import com.example.demo.pojo.User;
import com.example.demo.pojo.constant.GlobalConstant;
import com.example.demo.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@Component
@Slf4j
public class JWTFilter   extends OncePerRequestFilter {
    //user查询数据库层
    @Autowired
    private UserMapper userMapper;

    // 私钥
    public Key key= Keys.hmacShaKeyFor( GlobalConstant.key.getBytes());
    // 有效时间
    public long tokenValidityInMilliseconds=GlobalConstant.tokenValidityInMilliseconds;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWTFilter  doFilterInternal  :time:"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd hh:mm:ss"));
        //从url中根据参数获取值
        String jwt = obtainParameter(request, GlobalConstant.jwt);
        Authentication authentication=null;
        if (StringUtils.hasText(jwt)) {
            try {
                //考虑缓存
                authentication= getAuthentication( jwt ,WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean(UserMapper.class));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch (io.jsonwebtoken.ExpiredJwtException e){
                //jwt过期
                log.error("认证过期");
                response.setContentType("application/json;charset=utf-8");
                ObjectMapper mapper=new ObjectMapper();
                // 添加一个map对象，方便等下转换成字符串
                Map json = new HashMap<String ,Object>();
                json.put("code",GlobalConstant.accountExpired);
                PrintWriter out = response.getWriter();
                out.write(mapper.writeValueAsString(json));
                out.println();
                out.flush();
                out.close();
            }
        }
        filterChain.doFilter(request, response);
    }

    //通过head头拿到信息
    private String obtainParameter(HttpServletRequest request, String parameter) {
       try {
           return  request.getHeader(parameter);
       }
       catch (NullPointerException e){
           log.info("NullPointerException");
           return "";
       }
    }
    //获取权限(私有方法获取不到userMapper)
    public Authentication getAuthentication(String token,UserMapper userMapper) {
        // 根据token获取用户信息
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        User user = JSONUtil.toBean(claims.getSubject(), User.class);
        // 2. 设置权限
        Collection<GrantedAuthority> grantedAuthorities =   user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, token, grantedAuthorities);
    }

    /**
     * 创建令牌
     * @param  authentication登录信息
     * @param ttlMillis  过期时间
     * */
    public  String createJWT(Authentication authentication, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        Map<String,Object> claims = new HashMap<String,Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setSubject(JsonUtils.deserializer(authentication.getPrincipal()))        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //设置过期时间
        }
        return builder.compact();  //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
    }
}

