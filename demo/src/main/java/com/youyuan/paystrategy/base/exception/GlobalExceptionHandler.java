package com.youyuan.paystrategy.base.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by GengHongJun on 2018/6/14.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception e)  {
//      // If the exception is annotated with @ResponseStatus rethrow it and let
//      // the framework handle it - like the OrderNotFoundException example
//      // at the start of this post.
//      // AnnotationUtils is a Spring Framework utility class.
//      if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
//          throw e;
        System.out.println("GlobalDefaultExceptionHandler.defaultErrorHandler():"+e.getMessage()+"==="+e.getCause());

//      // Otherwise setup and send the user to a default error-view.
      ModelAndView mav = new ModelAndView("error");
//      mav.addObject("exception", e);
      mav.addObject("msg",e.getMessage());
//      mav.addObject("url", req.getRequestURL());
//      return mav;

        //打印异常信息：
      //  e.printStackTrace();

//        return "error";
       /*
        * 返回json数据或者String数据：
        * 那么需要在方法上加上注解：@ResponseBody
        * 添加return即可。
        */

       /*
        * 返回视图：
        * 定义一个ModelAndView即可，
        * 然后return;
        * 定义视图文件(比如：error.html,error.ftl,error.jsp);
        *
        */
    }
}
