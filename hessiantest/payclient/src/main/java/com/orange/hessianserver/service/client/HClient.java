package com.orange.hessianserver.service.client;

import com.caucho.hessian.client.HessianProxyFactory;

import java.util.HashMap;
import java.util.Map;

public class HClient {
    private  final static String DEFAULT="http://192.168.33.47:8081/";
    private final static Map<String, Object> instance = new HashMap<String, Object>();
    private final static HessianProxyFactory factory = new HessianProxyFactory();

    static {
        factory.setOverloadEnabled(true);    //设置远程调用时是否重载方法
        factory.setHessian2Request(true); //设置是否使用Hessian版本2协议解析请求
        factory.setHessian2Reply(true);//设置是否使用Hessian版本2协议解析响应
    }

    /**
     * 获取对外service接口实例化
     *
     * @param classOfT 调用接口类名
     * @param url      调用接口地址
     * @return 返回调用接口的对象服务services
     */
    @SuppressWarnings("unchecked")
    public synchronized static <T> T getService(Class<T> classOfT, String url) {
        if (null == classOfT || null == url || "".equals(url)) {
            return null;
        }
        if (null != instance.get(url)) {
            return (T) instance.get(url);
        } else {
            T o = null;
            try {
                o = (T) factory.create(classOfT, url);
                if (o != null) {
                    instance.put(url, o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return o;
        }

    }

    /**
     * 获取对外service接口实例化
     *
     * @param classOfT 调用接口类名
     * @return 返回调用接口的对象服务services
     */
    @SuppressWarnings("unchecked")
    public synchronized static <T> T getService(Class<T> classOfT) {
        String defaultUrl = DEFAULT;
        defaultUrl = defaultUrl + classOfT.getSimpleName().replaceFirst(classOfT.getSimpleName().charAt(0) + "", (char) (classOfT.getSimpleName().charAt(0) + 32) + "");
        if (null != instance.get(defaultUrl)) {
            return (T) instance.get(defaultUrl);
        } else {
            T o = null;
            try {
                o = (T) factory.create(classOfT, defaultUrl);
                if (o != null) {
                    instance.put(defaultUrl, o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return o;
        }

    }


}