package com.example.demo.config.zookeeper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;


@SuppressWarnings("all")
@Slf4j
@Component
@Configuration
@PropertySource(value = "classpath:zk/application.properties")
@Data
public class ZKClient {
    @Value("${zkServer}")
    public String zkServer;
    @Value("${connectTimeout}")
    public int connectTimeout;//连接zk超时时间
    @Value("${sessionTimeout}")
    public int sessionTimeout;//zk-session超时时间
    @Value("${namespace}")
    public String namespace;  //名称空间
    @Value("${lockPath}")
    public String lockPath; //锁的路径


    @Bean
    public CuratorFramework zkClient(){
        //拒绝链接充实次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //通过工厂创建Curator
        CuratorFramework zkClient = CuratorFrameworkFactory.builder().connectString(zkServer)
                .sessionTimeoutMs(sessionTimeout).
                        connectionTimeoutMs(connectTimeout).
                        retryPolicy(retryPolicy).
                        namespace(namespace). //名称空间的操作
                build();
        zkClient.getConnectionStateListenable().addListener(connectionState());
        zkClient.start();  //todo 测试先关闭
        return  zkClient;
    }

    /**
     * 连接状态监听
     */
    public ConnectionStateListener connectionState(){
        ConnectionStateListener listener= new ConnectionStateListener(){
            @Override
            public void stateChanged(CuratorFramework zkClient, ConnectionState state) {
                try {
                    //初始化创建节点
                    if(state.equals(ConnectionState.CONNECTED)){
                        log.info("客户端连接开启");

                    }
                    if(state.equals(ConnectionState.RECONNECTED)){
                        log.info("客户端重新连接");

                    }
                    if(state.equals(ConnectionState.LOST)){
                        log.info("客户端连接丢失");
                    }
                }
                catch (Exception e){
                    log.error(e.getMessage());
                }
            }
        };
        return  listener;
    }


    @Bean
    public InterProcessSemaphoreMutex zklock(){
        return new InterProcessSemaphoreMutex (zkClient(),lockPath);
    }
}
