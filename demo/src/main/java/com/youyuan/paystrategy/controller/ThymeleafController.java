package com.youyuan.paystrategy.controller;

import java.util.Locale;
import java.util.Map;

import com.youyuan.paystrategy.base.common.LocaleMessageSourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模板测试.
 * @author Administrator
 *
 */
@Controller
public class ThymeleafController {

    @Resource
    private LocaleMessageSourceService localeMessageSourceService;
    /**
     * 返回html模板.
     */
    @RequestMapping("/helloHtml")
    public String helloHtml(Map<String,Object> map){
       map.put("hello","from TemplateController.helloHtml");
        String msg3 = localeMessageSourceService.getMessage("welcome");
        System.out.println(msg3);
       return"/helloHtml";
    }
    /**
     * 返回html模板.
     */
    @RequestMapping("/helloJsp")
    public String helloJsp(Map<String,Object> map){
        map.put("hello","from TemplateController.helloHtml");
        return"/helloHtml";
    }

//    @RequestMapping("/changeSessionLanauage")
//    public String changeSessionLanauage(HttpServletRequest request, String lang){
//        System.out.println(lang);
//        if("zh".equals(lang)){
//            //代码中即可通过以下方法进行语言设置
//            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh", "CN"));
//        }else if("en".equals(lang)){
//            //代码中即可通过以下方法进行语言设置
//            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en", "US"));
//        }
//        return "redirect:/helloHtml";
//    }
    @RequestMapping("/changeSessionLanauage")
    public String changeSessionLanauage(HttpServletRequest request, HttpServletResponse response, String lang){
        System.out.println(lang);
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if("zh".equals(lang)){
            localeResolver.setLocale(request, response, new Locale("zh", "CN"));
        }else if("en".equals(lang)){
            localeResolver.setLocale(request, response, new Locale("en", "US"));
        }
        return "redirect:/helloHtml";
    }
}