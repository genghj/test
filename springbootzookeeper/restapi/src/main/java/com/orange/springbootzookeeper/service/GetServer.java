//package com.orange.springbootzookeeper.service;
//
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class GetServer {
//
//    //本地缓存服务列表
//    private static Map<String, List<String>> servermap;
//
//    public Watcher myWatcher = new Watcher() {
//        @Override
//        public void process(WatchedEvent watchedEvent) {
//            //如果服务节点数据发生变化则清空本地缓存(这种做法有点欠佳)
//            if (watchedEvent.getType().equals(Event.EventType.NodeChildrenChanged)) {
//                servermap = null;
//            }
//        }
//    };
//
//    private List<String> getNodeList(String serverName) throws KeeperException, InterruptedException {
//        if (servermap == null) {
//            servermap = new HashMap<>();
//        }
//
////        ZooKeeper zooKeeper = Zook.zooKeeper;
//        Stat exists = null;
//        Watcher existsWatcher = new Watcher() {
//            @Override
//            public void process(WatchedEvent watchedEvent) {
//                System.out.println("sssss");
//            }
//        };
//         ZooKeeper zooKeeper;
//        try {
//            zooKeeper = new ZooKeeper("192.168.33.47:2182,192.168.33.47:2181,192.168.33.47:2183", 9999, myWatcher);
//
//            String s = "/xxs/" + serverName;
//            exists = zooKeeper.exists(s, existsWatcher);
//        } catch (Exception e) {
//        }
//
//        //判断是否存在该服务
//        if (exists == null) return null;
//        List<String> serverList = servermap.get("serverName");
//        if (serverList != null && serverList.size() > 0) {
//            return serverList;
//        }
//        List<String> children = zooKeeper.getChildren("/" + serverName, myWatcher);
//        List<String> list = new ArrayList<>();
//        for (String s : children) {
//            byte[] data = zooKeeper.getData("/" + serverName + "/" + s, myWatcher, null);
//            list.add(new String(data));
//        }
//        servermap.put(serverName, list);
//        return list;
//    }
//
//    public String getServerinfo(String serverName) {
//        try {
//            List<String> nodeList = getNodeList(serverName);
//            if (nodeList == null|| nodeList.size()<1) {
//                return null;
//            }
//            //这里使用得随机负载策略，如需需要自己可以实现其他得负载策略
//            String s = nodeList.get((int) (Math.random() * nodeList.size()));
//            return s;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}