package com.youyuan.paystrategy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youyuan.paystrategy.base.config.datasource.dynamic.TargetDataSource;
import com.youyuan.paystrategy.bean.Hello;
import com.youyuan.paystrategy.dao.HelloDao;
import com.youyuan.paystrategy.mapper.HelloMappper;
import com.youyuan.paystrategy.repository.HelloRepository;
import com.youyuan.paystrategy.service.HelloService;
import com.youyuan.paystrategy.task.Task2;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 提供Demo服务类.
 * @author Administrator
 *
 */
@Service
public class HelloServiceImpl implements HelloService{
    /**
     * 启动的时候观察控制台是否打印此信息;
     */
    public HelloServiceImpl() {
        System.out.println("HelloServiceImpl.HelloServiceImpl()");
    }

    @Resource
    private HelloRepository helloRepository;
    @Resource
    private HelloDao helloDao;
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HelloMappper helloMappper;

    public List<Hello> likeName(String name, int pageNum, int pageSize){
        //第一个参数是第几页；第二个参数是每页显示条数。
        PageHelper.startPage(pageNum, pageSize).setOrderBy("id");
        List<Hello> list =helloMappper.likeName(name);
//        PageInfo<Hello> p = new PageInfo<Hello>(list);
      return   list;
    }

    @Transactional
    @CachePut(value="hello",key="'hello_'+#demo.getId() ")
    public Hello save(Hello demo){
//        helloDao.save(demo);
       return helloRepository.save(demo);
    }
//    value属性表示使用哪个缓存策略，缓存策略在ehcache.xml。
    //将缓存保存进andCache，并使用参数中的userId加上一个字符串(这里使用方法名称)作为缓存的key
//   @Cacheable(value="andCache",key="#userId + 'findById'")
    @Cacheable(value="hello",key="'hello_'+#id ",condition="#id > 2") //将缓存保存进andCache，并当参数userId的长度大于2时才保存进缓存，默认使用参数值及类型作为缓存的key
    public Hello getById(long id){
        //demoRepository.findOne(id);//在demoRepository可以直接使用findOne进行获取.
        System.out.println("getById ====数据库取得。。id="+id);
//        return helloDao.getById(id);
        return helloMappper.getById(id);
    }
    public void saveStringToRedis(String key ,String value){
        if(key!=null) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            System.out.println(valueOperations.get(key));
        }
    }
    @CacheEvict(value="hello",key="'hello_'+#id ",condition="#id > 2")
     public void deleteFromCache(long id) {
        System.out.println("delete().从缓存中删除.id="+id);
    }

    /**
     * 不指定数据源使用默认数据源
     * @return
     */
    public List<Hello> getList(){
        return helloDao.getList();
    }

    /**
     * 指定数据源
     * @return
     */
    @TargetDataSource("ds1")
    public List<Hello> getListByDs1(){
        return helloDao.getListByDs1();
    }

}