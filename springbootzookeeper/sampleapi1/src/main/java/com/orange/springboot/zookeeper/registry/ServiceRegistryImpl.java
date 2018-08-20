package com.orange.springboot.zookeeper.registry;

import com.springbootzookeeper.registry.ServiceRegistry;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;


@Component
public class ServiceRegistryImpl implements Watcher, ServiceRegistry {
    private static final int SESSION_TIMEOUT = 5000;
    private static final String REGISTRY_PATH = "/registry";
    private static CountDownLatch latch = new CountDownLatch(1);
    private static Logger logger = LoggerFactory.getLogger(ServiceRegistryImpl.class);
    private ZooKeeper zk;

    public ServiceRegistryImpl() {
        logger.info(" ServiceRegistryImpl 初始化类");
    }

    public ServiceRegistryImpl(String zkServers) {
        try {
            zk = new ZooKeeper(zkServers, SESSION_TIMEOUT, this);
            latch.await();
            logger.info("connected to zookeeper");

        } catch (Exception e) {
            logger.error("create zookeeper client failuer", e);
        }
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        String registryPath = REGISTRY_PATH;
        try {
            logger.info("-zk---------"+zk);
            //创建根节点：持久节点
            if (zk.exists(registryPath, false) == null) {
                zk.create(registryPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
                logger.info("create registry node:{}",registryPath);
            }
            //创建服务节点：持久节点
            String servicePath=registryPath+"/"+serviceName;
            if(zk.exists(servicePath, false)==null){
                zk.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                logger.info("create service node :{}"+servicePath);
            }
            //创建地址节点：临时顺序节点
            String addressPath=servicePath+"/address-";
            String addressNode=zk.create(addressPath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.info("create node address:{}=>{}"+addressNode);
        } catch (Exception e) {
            logger.error("create node failure",e);
        }

    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }

    }

}