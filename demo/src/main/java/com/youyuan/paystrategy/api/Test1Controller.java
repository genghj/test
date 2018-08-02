package com.youyuan.paystrategy.api;

import com.youyuan.paystrategy.bean.DemoInfo;
import com.youyuan.paystrategy.bean.Hello;
import com.youyuan.paystrategy.service.HelloService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.annotation.Resource;

/**
 * Swagger2默认将所有的Controller中的RequestMapping方法都会暴露，然而在实际开发中，我们并不一定需要把所有API都提现在文档中查看，这种情况下，使用注解@ApiIgnore来解决，如果应用在Controller范围上，则当前Controller中的所有方法都会被忽略，如果应用在方法上，则对应用的方法忽略暴露API。
 
注解@ApiOperation和@ApiParam可以理解为API说明，多动手尝试就很容易理解了。
如果我们不使用这样注解进行说明，Swagger2也是有默认值的，没什么可说的试试就知道了。
 
在 http://localhost:8080/swagger-ui.html 显示页面的右上角有api_key ，springfox-swagger 2.2.2 版本并没有进行处理，我们可以自己添加拦截器拦截 /v2/api-docs来处理我们API文档的访问权限，如果要更严格更灵活的控制，可能需要修改源码来实现了。相信 springfox-swagger 的后期版本应该会支持更全面的应用需求的。
 *
 * 最常用的5个注解
 @Api：修饰整个类，描述Controller的作用
 @ApiOperation：描述一个类的一个方法，或者说一个接口
 @ApiParam：单个参数描述
 @ApiModel：用对象来接收参数
 @ApiProperty：用对象接收参数时，描述对象的一个字段

 其它若干
 @ApiResponse：HTTP响应其中1个描述
 @ApiResponses：HTTP响应整体描述
 @ApiClass
 @ApiError
 @ApiErrors
 @ApiParamImplicit
 @ApiParamsImplicit

  * @version v.0.1
 */
@Controller
@Api(value = "测试任务管理", tags = "测试任务管理API", description = "描述信息")
@RequestMapping("/api/test")
public class Test1Controller {

    @Resource
    private HelloService helloService;
    @ResponseBody
    @RequestMapping(value = "/show", method=RequestMethod.POST)// 这里指定RequestMethod，如果不指定Swagger会把所有RequestMethod都输出，在实际应用中，具体指定请求类型也使接口更为严谨。
    @ApiOperation(value="测试接口", notes="测试接口详细描述")
    public String show(
            @ApiParam(required=true, name="name", value="姓名")
            @RequestParam(name = "name") String stuName){
        return "success";
    }



    @ApiOperation(value = "添加Hello", notes = "获取Hello信息(用于数据同步)", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    // @RequestBody只能有1个
    // 使用了@RequestBody，不能在拦截器中，获得流中的数据，再json转换，拦截器中，也不清楚数据的类型，无法转换成java对象
    // 只能手动调用方法
    public String add(@RequestBody Hello hello) {
        Hello u = helloService.save(hello);
        System.out.println(u);
        return null;
    }

    @ApiOperation(value = "update Hello", notes = "获取Hello(用于数据同步)", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(Hello hello) {
        Hello u = helloService.save(hello);
        System.out.println(u);
        return null;
    }

}