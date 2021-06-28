package com.example.demo.jobdetail;


import com.example.demo.config.zookeeper.ZKClient;
import com.example.demo.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


@Slf4j
public abstract class ZkQuartzJobBean extends QuartzJobBean {


    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        CuratorFramework zkClient= SpringUtil.getBean(CuratorFramework.class);
        ZKClient client = SpringUtil.getBean(ZKClient.class);
        String node="";
        try {
            node= zkClient.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL) //零时顺时节点
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)//只有创建者才有ACL权限
                    .forPath(client.getLockPath());
            if(StringUtils.isNotEmpty(node)){
                run(jobexecutioncontext);
            }
        }
        catch ( org.apache.zookeeper.KeeperException e){
            log.error("拒绝执行任务");
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if(StringUtils.isNotEmpty(node)){
                    //todo 延迟一秒钟删除  前一个任务锁已经删除
                    Thread.sleep(1000);
                    zkClient.delete().forPath(node);
                }
            }
            catch (Exception e){log.error("删除节点失败:{}",e); }
        }
    }
    abstract void run(JobExecutionContext jobexecutioncontext);
}
