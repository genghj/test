package com.youyuan.paystrategy.controller;

import ch.qos.logback.classic.LoggerContext;
import com.github.pagehelper.PageInfo;
import com.youyuan.paystrategy.base.common.LocaleMessageSourceService;
import com.youyuan.paystrategy.base.config.SetProperties;
import com.youyuan.paystrategy.bean.Hello;
import com.youyuan.paystrategy.service.HelloService;
import com.youyuan.paystrategy.task.Task2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by GengHongJun on 2018/6/14.
 */
@RequestMapping(value="/hellos")// 通过这里配置使下面的映射都在/users下
@Controller
public class HelloController {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired
    SetProperties setProperties;
    @Resource
    private HelloService helloService;


    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    /**
     * 此方法执行的时候，会抛出异常：
     * Session creation has been disabled for the current subject.
      * @return
     */
    @ResponseBody
    @RequestMapping("/hello3")
    public String hello3(){
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        System.out.println(session);
        return"hello3,Andy";
    }
    @ResponseBody
    @RequestMapping("/helloShiro")
    public String helloShiro(String params1,String params2){
        return "hello,Andy,params1="+params1+",params1="+params2;
    }
    @ResponseBody
    @RequestMapping("/hello4")
    @RequiresRoles("vip")
// @RequiresPermissions("userInfo:add")//权限管理;
    public String hello4(){
        return "hello4,Andy";
    }
    @RequestMapping("/")
    public String hello(){
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        String packageName ="com.youyuan";

        System.out.println( "loggerLevel:"+ loggerContext.getLogger(packageName).getLevel());
        logger.info("logger--hello->hello.jsp");
        return "hello";
    }
    @RequestMapping("/hello")
    public String hello(Map<String,Object> map){
        map.put("name", "[Angel -- 守护天使]");
        map.put("gender",1);//gender:性别，1：男；0：女；

        List<Map<String,Object>> friends =new ArrayList<Map<String,Object>>();
        Map<String,Object> friend = new HashMap<String,Object>();
        friend.put("name", "张三");
        friend.put("age", 20);
        friends.add(friend);
        friend = new HashMap<String,Object>();
        friend.put("name", "李四");
        friend.put("age", 22);
        friends.add(friend);
        map.put("friends", friends);
        return "hello";
    }
    @RequestMapping("/getHello")
    public Hello getHello(){
        logger.info(setProperties.toString());
        logger.info("logger--getHello-> setProperties.age:"+setProperties.getAge()+" name:"+setProperties.getName());
        System.out.println("setProperties age:"+setProperties.getAge()+" name:"+setProperties.getName());
        Hello hello= new Hello();
        hello.setId(1231223l);
        hello.setName("check this name!11");
        hello.setDate(new Date());
        return hello;
    }
    @RequestMapping("/zeroException")
    public int zeroException(){
        return 100/0;
    }

    @RequestMapping("/saveHello")
    public String saveHello(){
        Hello hello = new Hello();
        hello.setDate(new Date());
        hello.setName("Angel");
        helloService.save(hello);//保存数据.
        return"ok.HelloController.save"+hello.toString();
    }
    @RequestMapping("/getHelloById")
    public Hello getById(long id){
        logger.info("logger--getById->"+id);
//        helloService.deleteFromCache(id);
        return helloService.getById(id);
    }

    @RequestMapping("/likeName")
    public List<Hello> likeName(String name, int pageNum, int pageSize){
        List<Hello> list =   helloService.likeName(name,pageNum,pageSize);
        return  list;
    }
    @RequestMapping("/getHelloList")
    public String getHelloList(){
     for(Hello d:helloService.getList()){
         System.out.println(d);
         logger.info("logger--getHelloList->"+d.toString());
     }
        for(Hello d:helloService.getListByDs1()){
            System.out.println(d);
        }
        return"ok";
    }

    //这里为了方便测试，直接将数据存储在map中，实际请从数据库获取.
    private static Map<Long,Hello> hellos = Collections.synchronizedMap(new HashMap<Long,Hello>());

    /**
     * 返回所有的用户.
     * @return
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public List<Hello> getUserList() {
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        List<Hello> r = new ArrayList<Hello>(hellos.values());
        return r;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Hello getHello(@PathVariable Long id) {
        // 处理"/Hellos/{id}"的GET请求，用来获取url中id值的Hello信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return hellos.get(id);
    }

    /**
     * post 保存用户.
     * @param Hello
     * @return
     */
    @RequestMapping(value = "",method=RequestMethod.POST)
    public String postHello(Hello Hello){
        // 处理"/Hellos/"的POST请求，用来创建Hello
        //@ModelAttribute Hello Hello
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        hellos.put(Hello.getId(), Hello);
        return"success";
    }

    /**
     * 使用put 进行更新用户.
     * @param id
     * @param Hello
     * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public String putHello(@PathVariable Long id,Hello Hello){
        // 处理"/Hellos/{id}"的GET请求，用来获取url中id值的Hello信息
        Hello u = hellos.get(id);
        u.setName(Hello.getName());
        hellos.put(id, u);
        return"success";
    }

    /**
     * 使用delete删除用户.
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public String deleteHello(@PathVariable Long id) {
        // 处理"/Hellos/{id}"的DELETE请求，用来删除Hello
        // url中的id可通过@PathVariable绑定到函数的参数中
        hellos.remove(id);
        return"success";
    }

    @Autowired
    private Task2 task2;
    @RequestMapping("/task2")
    public String task1() throws Exception{
        task2.doTaskOne();
        task2.doTaskTwo();
        task2.doTaskThree();
        return"task2";
    }
}
