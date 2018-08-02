package com.youyuan.paystrategy.base.config;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Component
@ConfigurationProperties(prefix = "test")
@PropertySource("classpath:conf/setConf.properties")
public class SetProperties {
    @NotEmpty
    private String name;
    @Max(value = 1000)
    @Min(value = 1)
    private int age;
    @NotNull
    private List<String> companys = new ArrayList<String>();
    @NotEmpty(message="描述不能为空")
    @Length(min=6,message="描述长度不能小于6位")
    private String desc;
//    # 随机int
    private int number;
//    # 随机long
    private long bignumber;
//    # 10以内的随机数
    private int test1;
//    # 10-20的随机数
    private int test2;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getBignumber() {
        return bignumber;
    }

    public void setBignumber(long bignumber) {
        this.bignumber = bignumber;
    }

    public int getTest1() {
        return test1;
    }

    public void setTest1(int test1) {
        this.test1 = test1;
    }

    public int getTest2() {
        return test2;
    }

    public void setTest2(int test2) {
        this.test2 = test2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getCompanys() {
        return companys;
    }

    public void setCompanys(List<String> companys) {
        this.companys = companys;
    }

    @Override
    public String toString() {
        return "SetProperties{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", companys=" + companys +
                ", desc='" + desc + '\'' +
                ", number=" + number +
                ", bignumber=" + bignumber +
                ", test1=" + test1 +
                ", test2=" + test2 +
                '}';
    }
}
