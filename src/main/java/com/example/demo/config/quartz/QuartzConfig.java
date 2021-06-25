package com.example.demo.config.quartz;

import com.example.demo.config.pool.SimpleExecutorConfig;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
@Configuration
public class QuartzConfig {

    @Autowired
    private DataSource  dataSource;

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Bean
    public  JobFactory jobFactory(){
       //这个对象Spring会帮我们自动注入进来,也属于Spring技术范畴.
        //为什么需要这个类呢，在我写的这个demo中，大家可以将此类删掉，发现程序也可以政策运营，可是我为什么还是加上呢。
        //大家可以看下我们的任务类，大家可以看到Job对象的实例化过程是在Quartz中进行的，这时候我们将spring的东西注入进来，肯定是行不通的，所以需要这个类
        JobFactory jobFactory= new AdaptableJobFactory(){
            @Override
            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                //调用父类的方法
                Object jobInstance = super.createJobInstance(bundle);
                //进行注入
                capableBeanFactory.autowireBean(jobInstance);
                return jobInstance;
            }
        };
        return jobFactory;
    }


    // 指定quartz.properties，可在配置文件中配置相关属性
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    //线程池配置
    @Bean
    public Executor schedulerThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        return executor;
    }
    //配置任务调度工厂,用来生成任务调度器,这是quartz的核心
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        try {
            //是否覆盖已经存在的任务
            factory.setOverwriteExistingJobs(true);
            //配置数据源,这是quartz使用的表的数据库存放位置
            factory.setDataSource(dataSource);
            //设置定时任务的属性
            factory.setQuartzProperties(quartzProperties());
            //配置线程池
            factory.setTaskExecutor(schedulerThreadPool());
            //设置定时任务工厂
            factory.setJobFactory(jobFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }

    // 创建schedule
    @Bean(name = "scheduler")
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
