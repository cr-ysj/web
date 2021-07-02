package com.example.demo.config.mvc;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Springboot 到 2.4.1 版本之后才出现的这个问题，再结合报错信息提示不能使用*号设置允许的Origin
 * */
@Configuration
public class MvcConfig implements WebMvcConfigurer {




private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true); //sessionid 多次访问一致

    // 允许访问的客户端域名
    List<String> allowedOriginPatterns = new ArrayList<>();
    allowedOriginPatterns.add("*");
    corsConfiguration.setAllowedOriginPatterns(allowedOriginPatterns);
    corsConfiguration.addAllowedHeader("*"); // 允许任何头
    corsConfiguration.addAllowedMethod("*"); // 允许任何方法（post、get等）
    return corsConfiguration;
}

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
