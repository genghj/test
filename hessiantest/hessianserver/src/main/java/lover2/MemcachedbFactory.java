//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lover2;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemcachedbFactory {
    private static List<SockIOPool> pools = new ArrayList();
    private static String[] DB_HOSTS = new String[]{"192.168.33.47:11211"};
    public static final int memCacheNum = getMemcachedNum();
    public static boolean isOk = false;

    public MemcachedbFactory() {
    }
    @Bean
    public MemCachedClient memCachedClient(){
        return new MemCachedClient();
    }
    public static void init() {
        //DB_HOSTS = "mem1.wap.yyinter.cn:22422,mem2.wap.yyinter.cn:22422,mem3.wap.yyinter.cn:22422,mem4.wap.yyinter.cn:22422,mem5.wap.yyinter.cn:22422,mem6.wap.yyinter.cn:22422,mem7.wap.yyinter.cn:22422,mem8.wap.yyinter.cn:22422".split(",");

        for(int i = 0; i < DB_HOSTS.length; ++i) {
            SockIOPool pool = SockIOPool.getInstance("lover2wap" + i);
            String[] hosts = new String[]{DB_HOSTS[i]};
            pool.setServers(hosts);
            pool.setMaxConn(100);
            pool.setMinConn(5);
            pool.setInitConn(10);
            pool.setNagle(false);
            pool.setSocketTO(30000);
            pool.setSocketConnectTO(30000);
            pool.setAliveCheck(false);
            pool.setMaintSleep(30000L);
            pool.setMaxIdle(300000L);
            pool.initialize();
            pools.add(pool);
        }

        isOk = true;
    }

    private static int getMemcachedNum() {
       // String[] DB_HOSTS = "mem1.wap.yyinter.cn:22422,mem2.wap.yyinter.cn:22422,mem3.wap.yyinter.cn:22422,mem4.wap.yyinter.cn:22422,mem5.wap.yyinter.cn:22422,mem6.wap.yyinter.cn:22422,mem7.wap.yyinter.cn:22422,mem8.wap.yyinter.cn:22422".split(",");
        return DB_HOSTS != null?DB_HOSTS.length:0;
    }

    public static void destroy() {
        isOk = false;

        try {
            if(pools != null) {
                Iterator ex = pools.iterator();

                while(ex.hasNext()) {
                    SockIOPool pool = (SockIOPool)ex.next();
                    pool.shutDown();
                }
            }
        } catch (Exception var2) {
            ;
        }

    }
}
