package com.orange.hessianserver.service.impl;

//import cn.lover2.cache.LoverCache;
//import cn.lover2.cache.McClient;
import com.alibaba.fastjson.JSON;
import com.orange.hessianserver.service.StrategyService;
import com.orange.hessianserver.util.Constant;
import com.orange.hessianserver.util.RedisUtil;
import com.orange.hessianserver.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 1 支付开关获取
 * 2 支付方案获取
 * 3 文案配置获取
 * 4 根据实例id获取模块实例信息
 */
@Component
public class StrategyServiceImpl implements StrategyService {
    private final Logger logger = LoggerFactory.getLogger("StrategyServiceImpl");

    @Autowired
    private McClient mcClient;
    @Autowired
    private RedisUtil redisUtil;
    //====================配置平台3.0==start====================================
    // ------------------支付开关方案获取------start-----------

    /**
     * 根据集群 包名 版本获取支付信息direct:1 ：支付；2:代付（提现）
     * @param jiQun
     * @param version
     * @param packages
     * @param channel
     * @param direct  不传，默认为支付
     * @return
     */
    @Override
    public Map getPaySwitchInfoMap(String jiQun, String version, String packages, String channel , Integer direct){
        if(direct==null)direct= Constant.PAY_DIRECT_PAY;
        Map<String,Map<String,String>> map = new HashMap();
        String paySwitchKey= Constant.MCKEYPRE_CSBG_PAYSWITCH+"JQ_"+direct+"_"+jiQun;
        Map<Long, Map<String,String>> mapSet = (Map)getInfoFromCache( System.currentTimeMillis()+"",paySwitchKey,new HashMap<>());
        if(mapSet!=null) {
            Set<Long> keySet = mapSet.keySet();
            for (Long key : keySet) {
                Map innerMap = mapSet.get(key);
                if (innerMap != null) {
                    String fsAliasName = String.valueOf(innerMap.get("fsAliasName"));
                    String checkPackagesAll = String.valueOf(innerMap.get("packagesAll"));
                    String checkVersion = String.valueOf(innerMap.get("version"));
                    String checkPackages = String.valueOf(innerMap.get("packages"));
                    String checkChannel = String.valueOf(innerMap.get("channel"));
                    Set<String> channelConfilict = null;
                    if (Tools.notEmpty(checkChannel)) {
                        channelConfilict = Tools.conflictStrings(channel, checkChannel);
                    } else {
                        channelConfilict = new HashSet<>();
                        channelConfilict.add("channel");
                    }
                    if (channelConfilict != null && channelConfilict.size() > 0) {
                        String checkVersionResult = checkVersion(version, checkVersion);
                        if (Tools.notEmpty(checkVersionResult)) {
                            String conflictPackages = null;
                            if ("1".equals(checkPackagesAll)) {
                                conflictPackages = checkPackagesAll(checkPackages, packages, true);
                            } else {
                                conflictPackages = checkPackagesAll(checkPackages, packages, false);
                            }
                            if (Tools.notEmpty(conflictPackages) && Tools.notEmpty(fsAliasName)) {
                                map.put(fsAliasName, innerMap);
                            }
                        }
                    }
                }
            }
        }
        return map;
    }
    private Object getInfoFromCache(String logId, String key, Object type){
        Object reult=null;
        Object reultMc = mcClient.mGet(key);
        if(reultMc == null ){
            String reultRedis =String.valueOf(redisUtil.get(key)) ;// Tools.getInfoFromRedis( logId, key);
            reult=  JSON.toJavaObject(JSON.parseObject(reultRedis),type.getClass());
        }else{
            reult=reultMc;
        }
        return reult;
    }


    /**
     * 非全部不能为空,版本为545-555;556-999;1000;格式同渠道号[)
     * @param newString
     * @param oldString
     * @return
     */
    private String checkVersion(String newString, String oldString) {
        StringBuffer sb = new StringBuffer();
        if(Tools.isEmpty(newString)&&Tools.isEmpty(oldString)){
            sb.append("all version");
        }else if(Tools.isEmpty(newString)||Tools.isEmpty(oldString)){
            if(Tools.isEmpty(newString)){
                sb.append(oldString);
            }else{
                sb.append(newString);
            }
        }else if(Tools.notEmpty(newString)&&Tools.notEmpty(oldString)){
            Set<String> set = Tools.conflictStrings(newString,oldString);
            if(set!=null){
                for (String str:set) {
                    if(Tools.notEmpty(str))sb.append(str+"、");
                }
            }
        }
        return sb.toString();
    }
    /**
     * 非全部不能为空
     * @param quanBu
     * @param feiQuanBuSplit
     * @param isAll 是否有包全部为1
     * @return
     */
    private String checkPackagesAll(String quanBu, String feiQuanBuSplit, boolean isAll) {
        StringBuffer sb = new StringBuffer();
        if(Tools.isEmpty(quanBu)){
            sb.append(feiQuanBuSplit);
        }else{
            String[] fqbArray=  feiQuanBuSplit.split(getSplitFlag(feiQuanBuSplit));
            Set<String> set = new HashSet<>();
            for (String fqb:fqbArray) {
                if(isAll) {
                    if (quanBu.indexOf(fqb) == -1) {//没找到就视为冲突。
                        set.add(fqb) ;
                    }
                }else{
                    if (quanBu.indexOf(fqb)!= -1) {//找到就视为冲突。
                        set.add(fqb) ;
                    }
                }
            }
            for (String str:set) {
                if(Tools.notEmpty(str))    sb.append(str + ",");
            }
        }
        return sb.toString();
    }
    private String getSplitFlag(String str){
        String flag=";";
        if(str.indexOf("|")!=-1){
            flag="\\|";
        }
        if(str.indexOf(";")!=-1){
            flag=";";
        }
        if(str.indexOf(",")!=-1){
            flag=",";
        }
        return flag;
    }
    // ------------------支付开关方案获取------end-------------
    // ------------------支付方案获取------start-----------

    /**
     *
     * @param bizType  功能 ，bizType：zhiFu、wenAn  对应功能名称拼音驼峰全拼字符串
     * @param serviceType 功能名称字符串，vip douBi 等
     * @param userId
     * @param sex
     * @param channel
     * @param areaCode
     * @return
     */
    @Override
    public Map getStrategyInfoMap(String bizType, String serviceType, long userId, String sex , String channel, String areaCode){
        if(Tools.isEmpty(areaCode))areaCode ="0";
        if(Tools.isEmpty(bizType)||Tools.isEmpty(serviceType)||Tools.isEmpty(sex)||Tools.isEmpty(channel)){
            logger.error("获取运营方案参数校验失败！|method:getStrategyInfoMap|userId:"+userId +"|bizType:"+bizType+"|serviceType:"+serviceType+"|sex:"+sex+"|channel:"+channel+"|areaCode:"+areaCode);
            return null;
        }
        channel=channel.trim();
        areaCode=areaCode.trim();
        sex=sex.trim();
        bizType=bizType.trim();
        serviceType=serviceType.trim();
        String sexAll="2";
        String conditionAll="0";
        String key =Constant.MCKEYPRE_CSBG_PAYSTRATEGY+ bizType+"_"+serviceType+"_"+sex + "_" + (2 - (userId % 2)) +"_"+areaCode+"_"+channel;
        Map result=  (Map) mcClient.mGet( Constant.MCKEYPRE_CSBG_PAYSTRATEGY_USED+key);
        if(result==null||result.size()==0) {
//            String userGroup =getUserGroup(userId);
            result = getStrategyInfoReferInstanceMap( Constant.MCKEYPRE_CSBG_PAYSTRATEGY+ bizType+"_"+serviceType+"_"+sexAll + "_" +conditionAll+"_"+areaCode,  channel);//男女，单双
            if(result==null||result.size()==0){
                result = getStrategyInfoReferInstanceMap(  Constant.MCKEYPRE_CSBG_PAYSTRATEGY+ bizType+"_"+serviceType+"_"+sexAll + "_" +(2 - (userId % 2))+"_"+areaCode,  channel); //男女 单/双
            }
            if(result==null||result.size()==0){
                result = getStrategyInfoReferInstanceMap( Constant.MCKEYPRE_CSBG_PAYSTRATEGY+ bizType+"_"+serviceType+"_"+sex + "_" +conditionAll+"_"+areaCode,  channel);//男/女，单双
            }
            if(result==null||result.size()==0){
                result = getStrategyInfoReferInstanceMap( Constant.MCKEYPRE_CSBG_PAYSTRATEGY+ bizType+"_"+serviceType+"_"+sex + "_" +(2 - (userId % 2))+"_"+areaCode,  channel);//男/女，单/双
            }
            if(result==null) result = new HashMap();
            mcClient.mPut(Constant.MCKEYPRE_CSBG_PAYSTRATEGY_USED+key,result,120000);
        }

        return result;
    }
    //1关系获取

    /**
     * conditions 0全量1单号2双号
     *  sex 0 man 1 woman  2  all
     *  areaCode 1-349 999(all)
     * @param key
     * @param channel
     * @return
     */
    @Override
    public Map getStrategyInfoReferInstanceMap(String key, String channel){
        //关系key 找到对应的引用id
        Map instanceInfoMap= null;
        Map relationMap = null;
        relationMap = (Map) mcClient.mGet(key);//mc
        boolean fromRedis=false;
        if (relationMap == null) {
            String relationMapJsons = String.valueOf(redisUtil.get(key)) ;//Tools.getInfoFromRedis(System.currentTimeMillis() + "", key);
            if (relationMapJsons != null && relationMapJsons.length() > 20) {
                relationMap = JSON.toJavaObject(JSON.parseObject(relationMapJsons), Map.class);
                fromRedis=true;
            }
        }
        if(relationMap!=null) {
            Set<Object> keySet = relationMap.keySet();
            int effectLevel=-1;
            String instanceId=null;
            for (Object ids : keySet) {
                Map objMap = (Map) relationMap.get(ids);
                if(fromRedis && objMap==null){
                    objMap = (Map) relationMap.get(String.valueOf(ids));
                }
                if(objMap==null){
                    Long id = Long.valueOf(String.valueOf(ids));
                    objMap = (Map) relationMap.get(id);
                    if(objMap==null){
                        objMap = (Map) relationMap.get(String.valueOf(ids));
                    }
                }

                if (objMap != null) {
                    int referEffectLevel=   Integer.valueOf(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_EFFECTLEVEL)));
                    if(referEffectLevel>effectLevel){                            //还需要判断effectLevel
                        if (Tools.inChannel(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_CHANNEL)), channel)) {
                            boolean gotIt=false;
                            if(referEffectLevel==0){//时间
                                gotIt=true;
                            }else if(referEffectLevel==1 && Tools.inThisSets(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_EFFECTTIME)), String.valueOf(Integer.valueOf(new SimpleDateFormat("HHmm").format(new Date()))))){//时间
                                gotIt=true;
                            }else  if(referEffectLevel==2 && Tools.inThisSets(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_EFFECTTIME)), String.valueOf(Integer.valueOf(new SimpleDateFormat("HHmm").format(new Date()))))
                                    && Tools.inThisSets(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_EFFECTDATE)), String.valueOf(Integer.valueOf(new SimpleDateFormat("MMdd").format(new Date()))))){//日期
                                gotIt=true;
                            }
                            if(gotIt) {
                                String referInstanceId = String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_REFERINSTANCEID));
                                if (Tools.notEmpty(referInstanceId)) {
                                    instanceId = referInstanceId;
                                    effectLevel = referEffectLevel;
                                }
                            }
                        }
                    }
                }
            }
            if (instanceId != null) {
                instanceInfoMap = getInstanceInfoMap(instanceId);
            }
        }
        return instanceInfoMap;
    }
//    public Map getStrategyInfoReferInstanceMap( String key,String channel,String  userGroup){
//        //关系key 找到对应的引用id
//        Map  instanceInfoMap= null;
//        Map relationMap = null;
//        relationMap = (Map) McClient.mGet(key);//mc
//        boolean fromRedis=false;
//        if (relationMap == null) {
//            String relationMapJsons = Tools.getInfoFromRedis(System.currentTimeMillis() + "", key);
//            if (relationMapJsons != null && relationMapJsons.length() > 20) {
//                relationMap = JSON.toJavaObject(JSON.parseObject(relationMapJsons), Map.class);
//                fromRedis=true;
//            }
//        }
//        if(relationMap!=null) {
//            Set<Object> keySet = relationMap.keySet();
//            int effectLevel=-1;
//            String instanceId=null;
//            for (Object ids : keySet) {
//                Map objMap = (Map) relationMap.get(ids);
//                if(fromRedis && objMap==null){
//                    objMap = (Map) relationMap.get(String.valueOf(ids));
//                }
//                if(objMap==null){
//                    Long id =Long.valueOf(String.valueOf(ids));
//                    objMap = (Map) relationMap.get(id);
//                    if(objMap==null){
//                        objMap = (Map) relationMap.get(String.valueOf(ids));
//                    }
//                }
//
//                if (objMap != null) {
//                    int referEffectLevel=   Integer.valueOf(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_EFFECTLEVEL)));
//                    if(referEffectLevel>effectLevel){                            //还需要判断effectLevel
//                        if (Tools.inChannel(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_CHANNEL)), channel)) {
//                            boolean gotIt=false;
//                            if(referEffectLevel==0){//时间
//                                gotIt=true;
//                            }else if(referEffectLevel==1 && Tools.inThisSets(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_EFFECTTIME)),String.valueOf(Integer.valueOf(new SimpleDateFormat("HHmm").format(new Date()))))){//时间
//                                gotIt=true;
//                            }else  if(referEffectLevel==2 && Tools.inThisSets(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_EFFECTTIME)),String.valueOf(Integer.valueOf(new SimpleDateFormat("HHmm").format(new Date()))))
//                                    && Tools.inThisSets(String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_EFFECTDATE)),String.valueOf(Integer.valueOf(new SimpleDateFormat("MMdd").format(new Date()))))){//日期
//                                gotIt=true;
//                            }
//                            if(gotIt) {//增加多维度用户组的判断。
//                                //组判断---start
//                                boolean inGroup=checkUserGroup(userGroup,objMap);
//                                //组判断---end
//                                if(inGroup) {
//                                    String referInstanceId = String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_REFERINSTANCEID));
//                                    if (Tools.notEmpty(referInstanceId)) {
//                                        instanceId = referInstanceId;
//                                        effectLevel = referEffectLevel;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if (instanceId != null) {
//                instanceInfoMap = getInstanceInfoMap(instanceId);
//            }
//        }
//        return instanceInfoMap;
//    }

////
////    private boolean checkUserGroup(String labelSets, Map objMap) {
////        boolean result=false;
////        try{
////            String csifLabelSets=   String.valueOf(objMap.get(Constant.RELATIONSHIP_KEY_LABLE_SETS));
////            if(Tools.isEmpty(labelSets)||Tools.isEmpty(csifLabelSets)){
////                result=true;
////            }else{
////                String temp=null;
////                if(labelSets.length()>csifLabelSets.length()){
////                    temp=csifLabelSets;
////                    csifLabelSets=labelSets;
////                    labelSets=temp;
////                }
////                String cs = getSeparator(csifLabelSets);
////                String ls = getSeparator(labelSets);
////                String[] lsArray = labelSets.split(Tools.isEmpty(ls)?";":ls);
////                for (String  csinfo:lsArray ) {
////                    if(Tools.notEmpty(csinfo)) {
////                        if(Tools.isEmpty(cs))cs =";";
////                        csifLabelSets = cs + csifLabelSets.trim() + cs;
////                        if (csifLabelSets.indexOf(cs + csinfo + cs) != -1) {
////                            result=true;
////                            break;
////                        }
////                    }
////                }
////            }
////        }catch (Exception e){
////            e.printStackTrace();
////        }
////        return result;
////    }
//
//
//    private String getSeparator(String groups) {
//        String separator="";
//        if(groups.indexOf(";")!=-1|| groups.indexOf("；")!=-1){
//            separator= ";";
//        }else if(groups.indexOf(",")!=-1|| groups.indexOf("，")!=-1){
//            separator=",";
//        }else if(groups.indexOf("|")!=-1){
//            separator="|";
//        }
//        return separator;
//    }
//
//    private String getUserGroup(long userId) {//通过三方接口请求数据,需要升级lover4全站做兼容，待今后做成服务完成解耦
//        try{
//            return "";
//        }catch (Exception e){
//            return "";
//        }
//    }
    //2.根据实例Id 获取实例map

    /**
     * 根据实例id 获取实例配置map
     * @param instanceId
     * @return
     */
    @Override
    public Map getInstanceInfoMap(String instanceId){
        String key = Constant.MCKEYPRE_CSBG_MODELSET_INSTANCE + instanceId;
        Map result = (Map) mcClient.mGet(key);
        if(result==null) {
            try {
                String resultStr = String.valueOf(redisUtil.get(key)) ;//Tools.getInfoFromRedis(System.currentTimeMillis() + "", key);
                Map mapFromRedis = JSON.toJavaObject(JSON.parseObject(resultStr), Map.class);
                if (mapFromRedis != null) result = mapFromRedis;
            }catch (Exception e){
                logger.error(" getInstanceInfoMap Id: "+instanceId + "errorMsg:"+e.getMessage());
            }
        }
        //  logger.info("getInstanceInfoMap result:"+JSON.toJSONString(result!=null?result:" null"));
        return result;
    }
    // ------------------支付方案获取------end-------------
    //====================配置平台3.0==end====================================

    @Override
    public Map setInstanceInfoMap(String key, String value) {
        Map map = new HashMap();
        map.put(key, value);
        map.put("test2", "你好啊，this is  a test.");
        map.put("test1", "人都发出去了，让我咋干。");
        mcClient.mPut("testkey","this is test value",5000l);
        System.out.println("testkey mExists:"+mcClient.mExists("testkey"));
        System.out.println("testkey testvalue:"+mcClient.mGet("testkey"));
        mcClient.mPut(key, map, 50000);
        Map result = (Map) mcClient.mGet(key);
        if (null != result){

            result.put("fech time", System.currentTimeMillis() + "");
            map =result;
        }else{
            map.put(" null time", System.currentTimeMillis() + "");
        }
        return map;
    }
}
