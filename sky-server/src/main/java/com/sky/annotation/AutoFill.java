package com.sky.annotation;

//自定义注解，需要字段公共联充处理

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//注解只能载在方法上,且只能是在运行时
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //操作类型insert,update
    OperationType value();
}
