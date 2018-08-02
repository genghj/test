package com.youyuan.paystrategy.jersey;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
import org.springframework.stereotype.Component;
 
@Path("/")
@Component
public class RestResource {
//    那么我们有更好的方案嘛，当然有，只需要在application.properties配置两个信息：
//    spring.http.encoding.force=true
//    spring.http.encoding.charset=UTF-8  替代SON+";charset=UTF-8")
    @GET
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/hello")
    public Map<String,Object> hello() {
       Map<String,Object> map = new HashMap<String,Object>();
       map.put("code","1");
        map.put("name","1");
       map.put("codeMsg", "success");
       return map;
    }
}