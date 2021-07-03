package com.example.demo.config.security.handler;

import com.example.demo.service.resource.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@SuppressWarnings("all")
@Component
/**
 * 用于表示对受权限保护的"安全对象"的权限设置信息
 * */
public class CustomerSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    //权限
    private HashMap<String, Collection<ConfigAttribute>> map =new HashMap<>();

    //资源
    @Autowired
    private IResourceService resourceService;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine() {
        //获取权限资源
        Map<String, List<String>> resourceAuth = resourceService.getAuthAndResource();
        Iterator<String> iterator = resourceAuth.keySet().iterator();
        String resoucePath = null;
        while (iterator.hasNext()) {
            Collection<ConfigAttribute> attributes=new ArrayList<>();
            //资源路径
            resoucePath = iterator.next();
            //权限
            List<String> auths = resourceAuth.get(resoucePath);
            for (int i = 0; i <auths.size() ; i++) {
                if(!attributes.contains(new SecurityConfig(auths.get(i)))){
                    attributes.add(new SecurityConfig(auths.get(i)));
                }
            }
            map.put(resoucePath, attributes);
        }
        this.map=map;
    }


    /**
     *
     * 用来判定用户是否有此权限。如果不在权限表中则放行
     *
     *
     *获取某个受保护的安全对象object的所需要的权限信息,是一组ConfigAttribute对象的集合，
     * 如果该安全对象object不被当前SecurityMetadataSource对象支持,则抛出异常IllegalArgumentException。
     * 该方法通常配合boolean supports(Class<?> clazz)一起使用，先使用boolean supports(Class<?> clazz)确保安全对象能被当前SecurityMetadataSource支持，
     * 然后再调用该方法。
     *
     * */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(map.size()==0){
            loadResourceDefine();
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            //判断请求是否有这个权限
            if(matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
      return  null;
    }

    /**
     * 获取该SecurityMetadataSource对象中保存的针对所有安全对象的权限信息的集合。
     * 该方法的主要目的是被AbstractSecurityInterceptor用于启动时校验每个ConfigAttribute对象
     * */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {

        return null;
    }

    /**
     * 这里clazz表示安全对象的类型，
     * 该方法用于告知调用者当前SecurityMetadataSource是否支持此类安全对象，
     * 只有支持的时候，才能对这类安全对象调用getAttributes方法
     * */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
