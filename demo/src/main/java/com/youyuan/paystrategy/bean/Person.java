package com.youyuan.paystrategy.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(  )
public class Person {
       private int id;//主键.  
       private String name;//姓名.  
       private int age;//年龄，女孩的年龄怎么能随便告诉你呢!  
       private double weight;//体重. 这个问女孩，女孩会￣へ￣的.
       private double height;//高

}  