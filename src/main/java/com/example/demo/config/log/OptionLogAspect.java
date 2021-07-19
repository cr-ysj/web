package com.example.demo.config.log;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.constant.GlobalConstant;
import com.example.demo.pojo.db.log.SysLog;
import com.example.demo.pojo.db.user.User;
import com.example.demo.pojo.enums.OptionLog;
import com.example.demo.service.log.ISystemLogService;
import com.example.demo.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面处理类，操作日志异常日志记录处理
 */
@SuppressWarnings("all")
@Aspect
@Component
public class OptionLogAspect {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(OptionLogAspect.class);

    @Autowired
    private ISystemLogService systemLogService;
    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.example.demo.pojo.enums.OptionLog)")
    public void optionLogPoinCut() { }



    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     * @param joinPoint 切入点
     * @param returnResult  返回结果
     */
    @AfterReturning(value = "optionLogPoinCut()", returning = "returnResult")
    public void saveOptionLog(JoinPoint joinPoint, Object returnResult) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        SysLog sysLog=new SysLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取注解内容
            OptionLog optionLog = method.getAnnotation(OptionLog.class);
            sysLog.setOptionModel(optionLog.optionModel());
            sysLog.setOptionType(optionLog.optionType());
            sysLog.setOptionDesc(optionLog.optionDesc());
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            sysLog.setOptionFunc(methodName); // 请求方法
            Map<String, String[]> parameterMap = request.getParameterMap();
            //请求参数
            Map<String, String[]> parames = request.getParameterMap();
            sysLog.setRequestParams(JSONUtil.toJsonStr(parames));
            sysLog.setResponseResult(String.valueOf(returnResult));// 返回结果
            sysLog.setOptionTime(new Date());//操作时间
            sysLog.setRequestUrl(request.getRequestURI());// 请求URI
            sysLog.setRequestIp(IPUtils.getIPAddress(request)); // 请求IP
            User principal =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            sysLog.setIsOptionError(!GlobalConstant.isOptionError); //非异常日志
            sysLog.setOptionUser(principal.getUsername());//操作员
            systemLogService.saveLog(sysLog);
        }
        catch (Exception e){
            log.info("日志记录异常:{}",e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "optionLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        SysLog sysLog=new SysLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取注解内容
            OptionLog optionLog = method.getAnnotation(OptionLog.class);
            sysLog.setOptionModel( optionLog.optionModel());
            sysLog.setOptionType(optionLog.optionType());
            sysLog.setOptionDesc(optionLog.optionDesc());
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            sysLog.setOptionFunc(methodName); // 请求方法
            Map<String, String[]> parameterMap = request.getParameterMap();
            Enumeration<String> names = request.getParameterNames();
            sysLog.setOptionTime(new Date());//操作时间
            sysLog.setRequestUrl(request.getRequestURI());// 请求URI
            sysLog.setRequestIp(IPUtils.getIPAddress(request)); // 请求URI
            User principal =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            sysLog.setOptionUser(principal.getUsername());//操作员
            sysLog.setIsOptionError(GlobalConstant.isOptionError); //异常日志
            StringBuffer exceptionMessage = new StringBuffer(e.getClass()+":"+e.getMessage()+System.getProperty("line.separator"));
            for (StackTraceElement stet : e.getStackTrace()) {
                exceptionMessage.append(stet + System.getProperty("line.separator"));
            }
            sysLog.setErrorMsg(String.valueOf(exceptionMessage)); // 异常信息
            systemLogService.saveLog(sysLog);
        }
        catch (Exception exception){
            log.info("日志记录异常:{}",exception.getMessage());
            exception.printStackTrace();
        }
    }
}
