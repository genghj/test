package com.orange.hessianserver.service;


import java.util.Map;

/**
 * 支付方案服务 三期
 *
 */
public interface StrategyService {

    //====================配置平台3.0==start====================================
    // ------------------支付开关方案获取------start-----------

    /**
     * 根据集群 包名 版本,渠道获取支付信息 direct:1 ：支付；2:代付（提现）
     * @param jiQun
     * @param version
     * @param packages
     * @param channel
     * @param direct 不传，默认为支付
     * @return
     */
    Map getPaySwitchInfoMap(String jiQun, String version, String packages, String channel, Integer direct);

    // ------------------支付开关方案获取------end-------------
    // ------------------支付方案获取------start-----------

    /**
     * 根据用户信息获取 相应的配置信息
     * @param bizType  功能 ，bizType：zhiFu、wenAn  对应功能名称拼音驼峰全拼字符串，根据配置平台配置文件（toolsConfig.yml）中字段值来确定
     * @param serviceType 功能名称字符串，vip douBi 等 具体定义需要根据配置平台配置文件（toolsConfig.yml）字段值来确定
     * @param userId
     * @param sex
     * @param channel
     * @param areaCode
     * @return
     */
    Map getStrategyInfoMap(String bizType, String serviceType, long userId, String sex, String channel, String areaCode);

    /**
     * 根据key获取相应方案关系，得到对应引用实例id.并获取相关实例数据 ，如果没有命中 返回null
     * @param key
     * @param channel
     * @return
     */
    Map getStrategyInfoReferInstanceMap(String key, String channel);

//    Map getStrategyInfoReferInstanceMap( String key,String channel,String  userGroup);
    /**
     * 根据实例id获取相应方案配置实例数据
     * @param instanceId
     * @return
     */
    Map getInstanceInfoMap(String instanceId);

    Map setInstanceInfoMap(String instanceId,String value);
 // ------------------支付方案获取------end-------------
    //====================配置平台3.0==end====================================
}
