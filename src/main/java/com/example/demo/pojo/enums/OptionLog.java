package com.example.demo.pojo.enums;


import java.lang.annotation.*;

/**
 * 操作日志注解
 * */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OptionLog {

    String optionModel() default "";  // 操作模块
    String optionType()  default "";  // 操作类型
    String optionDesc()  default "";  // 操作说明
}
