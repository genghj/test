package com.youyuan.paystrategy.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix="my")
public class ConfigYml {
   
    private List<String> servers = new ArrayList<String>();
   
    public List<String> getServers() {  
        return this.servers;
    }  
}  