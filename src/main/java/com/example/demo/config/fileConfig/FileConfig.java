package com.example.demo.config.fileConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Component
public class FileConfig {
    @Bean
    CommonsMultipartResolver commonsMultipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
        commonsMultipartResolver.setResolveLazily(false);
        commonsMultipartResolver.setMaxUploadSize(20971520);
        commonsMultipartResolver.setMaxUploadSizePerFile(20971520);
        return commonsMultipartResolver;
    }
}
