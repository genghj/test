package com.orange.hessianserver.util;


public class Constant {

	/** 支付开关状态*/
	public static final int SWITCH_USED = 1;
	public static final int SWITCH_STOP = 0;
	public static final int SWITCH_DELETE =2;
	/**
	 * 支付方向-支付
	 */
	public static final int PAY_DIRECT_PAY =1;
	/**
	 *支付方向-提现
	 */
	public static final int PAY_DIRECT_WITHDRAWAL=2;
	public static final String CACHE_KEY_MC="MC";  //缓存类型memcache
	public static final String CACHE_KEY_REDIS="REDIS";//缓存类型redis
	public static final String MCKEYPRE_CSBG_PAYSWITCH="csbg.youyuan.com.PaySwitch_";//支付开关前置key
	/**
	 *   String preKey =Constant.MCKEYPRE_CSBG_PAYSTRATEGY+ bizType+"_"+serviceType+"_"+sex + "_" + conditions+"_";	 *    key = preKey + areacode
	 *    Map<id,Map<channel/referInstanceId,value>>
	 */
	public static final String MCKEYPRE_CSBG_PAYSTRATEGY="csbg.youyuan.com.StrategyInfo_";//支付方案前置key
	public static final String MCKEYPRE_CSBG_PAYSTRATEGY_USED="USED_";//支付方案引用实例map
	public static final String MCKEYPRE_CSBG_MODELSET_INSTANCE="csbg.youyuan.com.modelset.instance_";//实例前置key
	public static final String CSBG_AREACODE_ALL="999";//全部地区特殊代码
	public static final String MCKEYPRE_CSBG_SEARCHE="csbg.strategy.obj.info.searche.map";//实例前置key

	public static final String RELATIONSHIP_KEY_CHANNEL="channel";//关系map中channelkey
	public static final String RELATIONSHIP_KEY_REFERINSTANCEID="referInstanceId";//关系map中referInstanceId

	public static final String RELATIONSHIP_KEY_LABLE_SETS="labelSets";//用户维度所属组key
	public static final String RELATIONSHIP_KEY_EFFECTLEVEL="effectLevel";//实例前置key
	public static final String RELATIONSHIP_KEY_EFFECTDATE="effectDate";//实例前置key
	public static final String RELATIONSHIP_KEY_EFFECTTIME="effectTime";//实例前置key
	public static final boolean NO_REDIS=false;//不用redis
}
