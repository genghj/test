package com.orange.hessianserver.service.impl;
//import cn.lover2.MemcachedbFactory;
//import cn.lover2.cache.memcachedb.LoverMemCachedClient;
//import cn.lover2.cache.memcachedb.MemcachedbCase;
//import cn.lover2.cache.memcachedb.StringKeyUtil;
//import cn.lover2.cache.utils.ClassUtil;
import com.danga.MemCached.MemCachedClient;
import com.schooner.MemCached.MemcachedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
@Component
public class McClient {
    @Autowired
    private MemCachedClient memCachedClient;
//
//    public static void put(Class ownerClass, String key, Object obj, long times) {
//        if(MemcachedbFactory.isOk) {
//            if(times >= 2592000000L) {
//                times = 2591999000L;
//            }
//
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum > 0) {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                if(obj == null) {
//                    System.out.println("MCERR0625A:" + combinkey(key, ownerClass));
//                }
//                 mc.set(combinkey(key, ownerClass), obj, new Date(times));
//             }
//
//        }
//    }
//
//    public static boolean cas(Class ownerClass, String key, Object obj, long times, long casUnique) {
//        if(!MemcachedbFactory.isOk) {
//            return false;
//        } else {
//            if(times >= 2592000000L) {
//                times = 2591999000L;
//            }
//
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum <= 0) {
//                return false;
//            } else {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                return mc.cas(combinkey(key, ownerClass), obj, new Date(times), casUnique);
//            }
//        }
//    }
//
//    public static Object get(Class ownerClass, String key) {
//        if(!MemcachedbFactory.isOk) {
//            return null;
//        } else {
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum <= 0) {
//                return null;
//            } else {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                Object o = mc.get(combinkey(key, ownerClass));
//                return o;
//            }
//        }
//    }
//
//    public static MemcachedItem gets(Class ownerClass, String key) {
//        if(!MemcachedbFactory.isOk) {
//            return null;
//        } else {
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum <= 0) {
//                return null;
//            } else {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                return mc.gets(combinkey(key, ownerClass));
//            }
//        }
//    }
//
//    public static void delete(Class ownerClass, String key) {
//        if(MemcachedbFactory.isOk) {
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum > 0) {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                mc.delete(combinkey(key, ownerClass));
//            }
//
//        }
//    }
//
//    public static boolean exists(Class ownerClass, String key) {
//        if(!MemcachedbFactory.isOk) {
//            return false;
//        } else {
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum <= 0) {
//                return true;
//            } else {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                return mc.keyExists(combinkey(key, ownerClass));
//            }
//        }
//    }
//
//    public static boolean add(Class ownerClass, String key, Object obj, long times) {
//        if(!MemcachedbFactory.isOk) {
//            return false;
//        } else {
//            if(times >= 2592000000L) {
//                times = 2591999000L;
//            }
//
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum <= 0) {
//                return false;
//            } else {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                return mc.add(combinkey(key, ownerClass), obj, new Date(times));
//            }
//        }
//    }
//
//    public static boolean replace(Class ownerClass, String key, Object obj, long times) {
//        if(!MemcachedbFactory.isOk) {
//            return false;
//        } else {
//            if(times >= 2592000000L) {
//                times = 2591999000L;
//            }
//
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum <= 0) {
//                return false;
//            } else {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                return mc.replace(combinkey(key, ownerClass), obj, new Date(times));
//            }
//        }
//    }
//
//    public static Collection<Object> get(Class ownerClass, String[] keys) {
//        if(!MemcachedbFactory.isOk) {
//            return null;
//        } else {
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum <= 0) {
//                return null;
//            } else {
//                HashMap m = new HashMap();
//                String[] arr$ = keys;
//                int len$ = keys.length;
//
//                for(int i$ = 0; i$ < len$; ++i$) {
//                    String key = arr$[i$];
//                    long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                    LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                    Object o = mc.get(combinkey(key, ownerClass));
//                    if(o != null) {
//                        m.put(key, o);
//                    }
//                }
//
//                return m.values();
//            }
//        }
//    }
//
//    private static String combinkey(String key, Class ownerClass) {
//        return ClassUtil.getShortName(ownerClass) + "_" + key;
//    }

    public   void mDelete(String key) {
        memCachedClient.delete(key);
//        if(MemcachedbFactory.isOk) {
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum > 0) {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                mc.delete(key);
//            }
//
//        }
    }

    public   void mPut(String key, Object obj, long times) {
        memCachedClient.set(key, obj, new Date(times));
//        if(MemcachedbFactory.isOk) {
//            if(times >= 2592000000L) {
//                times = 2591999000L;
//            }
//
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum > 0) {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                if(obj == null) {
//                    ;
//                }
//
//                mc.set(key, obj, new Date(times));
//            }
//
//        }
    }

    public   boolean mExists(String key) {
        return   memCachedClient.keyExists(key);
//        if(!MemcachedbFactory.isOk) {
//            return false;
//        } else {
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum <= 0) {
//                return true;
//            } else {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                return mc.keyExists(key);
//            }
//        }
    }

    public   Object mGet(String key) {
        return  memCachedClient.get(key);
//        if(!MemcachedbFactory.isOk) {
//            return null;
//        } else {
//            int memNum = MemcachedbFactory.memCacheNum;
//            if(memNum <= 0) {
//                return null;
//            } else {
//                long keyId = (long)(StringKeyUtil.getUserId(key) % memNum);
//                LoverMemCachedClient mc = MemcachedbCase.getInstance().getPool().getObject("lover2wap" + keyId);
//                Object o = mc.get(key);
//                return o;
//            }
//        }
    }
}
