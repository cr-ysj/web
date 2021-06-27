package com.example.demo.jobdetail;


import com.example.demo.config.zookeeper.ZKClient;
import com.example.demo.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
public abstract class ZkQuartzJobBean extends QuartzJobBean {
    //节点
    private  String node;
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        CuratorFramework zkClient= SpringUtil.getBean(CuratorFramework.class);
        ZKClient client = SpringUtil.getBean(ZKClient.class);
        try {
            node=zkClient.create()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL) //零时顺时节点
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)//只有创建者才有ACL权限
                    .forPath(client.getLockPath());
            String replace = StringUtils.replace(node, client.lockPath, "");
            Integer id= Integer.valueOf(replace);
            List<String> nodes = zkClient.getChildren().forPath("/");
            List<Integer> list=new ArrayList();
            for(String n:nodes){
                replace=StringUtils.replace("/"+n,client.lockPath,"");
                list.add( Integer.valueOf(replace));
            }
            Integer min = Collections.min(list);
            if(min==id){
                //执行定时任务
                log.info("start task:{}",DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                run(jobexecutioncontext);
                //解决节点删除的时候刚好有节点创建导致任务重复进入
                for (int i = 0; i <nodes.size() ; i++) {
                    zkClient.delete().forPath("/"+nodes.get(i));
                }
            }
            else {
                log.info("任务拒绝执行");
            }
        }
        catch (Exception e){
            log.error("任务执行失败{}",e.getMessage());
        }
    }
    abstract void run(JobExecutionContext jobexecutioncontext);
}
