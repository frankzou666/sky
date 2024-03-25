package com.sky.aspect;

//自定义类，实现公共字段处理

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //需要指定cut point,就是在哪里，哪个方法
    //通知又分为前置，后置，around
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {

    }

    //公共字段给值
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint)  {
        log.info("开始公共字段的填充");

        //获取当前被拦截到操作的方法类型
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //得到方法上的注解，这样确定是Insert还是Update
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        //获取操作的参数实体
        Object[] args = joinPoint.getArgs();
        if (args.length==0 || args==null) {
            return;
        }

        Object object = args[0];

        //准备给值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentUserid = BaseContext.getCurrentId();

        if (operationType==OperationType.INSERT) {
            try {
                Method setCreateTime = object.getClass().getDeclaredMethod("setCreateTime",LocalDateTime.class);
                Method setCreateUser =  object.getClass().getDeclaredMethod("setCreateUser",Long.class);
                Method setUpdateTime = object.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
                Method setUpdateUser =  object.getClass().getDeclaredMethod("setUpdateUser",Long.class);
                //为对像给值
                setCreateTime.invoke(object,now);
                setCreateUser.invoke(object,currentUserid);
                setUpdateTime.invoke(object,now);
                setUpdateUser.invoke(object,currentUserid);
            }  catch (Exception e) {
                e.printStackTrace();
            }

        } else if (operationType==OperationType.UPDATE) {
            try {
                Method setUpdateTime = object.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
                Method setUpdateUser =  object.getClass().getDeclaredMethod("setUpdateUser",Long.class);
                //为对像给值
                setUpdateTime.invoke(object,now);
                setUpdateUser.invoke(object,currentUserid);
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
