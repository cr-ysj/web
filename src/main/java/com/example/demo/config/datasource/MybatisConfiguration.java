package com.example.demo.config.datasource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@Configuration
@AutoConfigureAfter({ DataBaseConfiguration.class })
public class MybatisConfiguration {



    //事务管理工厂
    @Bean(name ="springManagedTransactionFactory" )
    @Primary
    public SpringManagedTransactionFactory springManagedTransactionFactory() {
        SpringManagedTransactionFactory springManagedTransactionFactory = new  SpringManagedTransactionFactory();
        return springManagedTransactionFactory;
    }

    //sqlSessionFactory配置
    @Primary
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, SpringManagedTransactionFactory springManagedTransactionFactory) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.demo.pojo");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        org.apache.ibatis.session.Configuration  configuration=new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(false);
        configuration.setCacheEnabled(true);
        configuration.setLogImpl(org.apache.ibatis.logging.log4j.Log4jImpl.class);
        configuration.setAggressiveLazyLoading(false);
        configuration.setLazyLoadingEnabled(true);
        Set<String> lazyLoadTriggerMethods =new HashSet<String>();
        lazyLoadTriggerMethods.add("toString");
        configuration.setLazyLoadTriggerMethods(lazyLoadTriggerMethods);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setTransactionFactory(springManagedTransactionFactory);
        return sqlSessionFactoryBean.getObject();
    }
    @Bean(name = "sqlSessionTemplate")
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    @Bean
    public MapperScannerConfigurer scannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        configurer.setBasePackage("com.example.demo.dao");
        configurer.setAnnotationClass(Mapper.class);
        return configurer;
    }
}
