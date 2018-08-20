package com.orange.springbootzookeeper.controller;

import org.apache.tomcat.jni.Thread;
import org.apache.zookeeper.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * rest响应的controller
 * @author bolingcavalry
 * @email zq2599@gmail.com
 * @date 2017/04/09 12:43
 */
@RestController
public class RestBizController {

    @RequestMapping(value = "/test" ,method = RequestMethod.GET)
    public Map test(String par){
        Map map = new HashMap();
        map.put("key1",par+"_key1value"+System.currentTimeMillis());
        map.put("key2",par+"_key2value"+System.currentTimeMillis());
        map.put("key3",par+"_key3value"+System.currentTimeMillis());
        map.put("key4",par+"_key4value"+System.currentTimeMillis());
        Map map1 = new HashMap();
        map1.put("key21",par+"_key1value"+System.currentTimeMillis());
        map1.put("key22",par+"_key2value"+System.currentTimeMillis());
        map.put("key5",map1);
        return map;
    }
    @RequestMapping(value = "/zkget" ,method = RequestMethod.GET)
    public String zkget(String sn,String n) {
        Watcher watcher= new Watcher(){
            public void process(WatchedEvent event) {
                System.out.println("receive event："+event);
            }
        };
        String s1=null;
        try {
            final ZooKeeper zk = new ZooKeeper("192.168.33.47:2182,192.168.33.47:2181,192.168.33.47:2183", 9999, watcher);
            System.out.println("=========创建节点===========");
            if(zk.exists("/test", false) == null)
            {
                zk.create("/test", "znode1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            System.out.println("=============查看节点是否安装成功===============");
            System.out.println(new String(zk.getData("/test", false, null)));

            System.out.println("=========修改节点的数据==========");
            zk.setData("/test", "zNode2".getBytes(), -1);
            System.out.println("========查看修改的节点是否成功=========");
            System.out.println(new String(zk.getData("/test", false, null)));

            System.out.println("=======删除节点==========");
            zk.delete("/test", -1);
            System.out.println("==========查看节点是否被删除============");
            System.out.println("节点状态：" + zk.exists("/test", false));



            System.out.println("=======HelloService get 节点==========");

            List<String> children = zk.getChildren("/"+n+"/"+sn, null);
            List<String> list = new ArrayList<>();
            for (String s : children) {
                byte[] data = zk.getData("/"+n+"/"+sn+"/"+ s, null, null);
                System.out.println(new String(data));
                list.add(new String(data));
            }
            System.out.println("list.size:"+list.size());
              s1 = list.get((int) (Math.random() * list.size()));
            System.out.println("list.s :"+s1 );
            zk.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return "get value from zookeeper [" + s1 + "]";
    }
}