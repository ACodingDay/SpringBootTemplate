package com.yyt.springbootdemo.aop;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.yyt.springbootdemo.annotation.LogRecordAnnotation;
import com.yyt.springbootdemo.domain.SysLog;
import com.yyt.springbootdemo.domain.SysUser;
import com.yyt.springbootdemo.service.SysLogService;
import com.yyt.springbootdemo.utils.FilterEntityUtil;
import com.yyt.springbootdemo.utils.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author yyt
 * @date 2021年11月01日 16:16
 * 注解 @Aspect 标注增强处理类（切面类）
 * 注解 @Component 表示交由 Spring 容器管理
 */
@Aspect
@Component
public class LogRecordAspect {

    @Autowired
    private SysLogService slService;

    // 给切入点织入自定义的属性
    @Pointcut("@annotation(com.yyt.springbootdemo.annotation.LogRecordAnnotation)")
    public void logAspect(){}

    // 获取自定义的属性
    public String[] getCustomAttributes(ProceedingJoinPoint point) throws Exception {
        // 获取连接点目标类名
        String targetName = point.getTarget().getClass().getName();
        // 获取连接点的签名的方法名
        String methodName = point.getSignature().getName();
        // 获取方法参数值数组
        Object[] args = point.getArgs();
        // 获取指定的类
        Class targetClass = Class.forName(targetName);
        // 获取类中的方法
        Method[] methods = targetClass.getMethods();
        String[] arrInfo = new String[2];
        for (Method method : methods){
            if(methodName.equals(method.getName())){
                Class[] clazzs = method.getParameterTypes();
                if(clazzs.length == args.length){
                    // 操作类型
                    arrInfo[0] = method.getAnnotation(LogRecordAnnotation.class).operateType();
                    // 操作说明
                    arrInfo[1] = method.getAnnotation(LogRecordAnnotation.class).operateDesc();
                    break;
                }
            }
        }
        return arrInfo;
    }

    // 在目标方法的前后都织入增强动作
    @Around("logAspect()")
    public Object recordLog(ProceedingJoinPoint point) throws Throwable {
        SysLog slog = new SysLog();

        // 获取当前线程绑定的请求对象，得到 session
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request= Objects.requireNonNull(attributes).getRequest();
        // HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SysUser user = (SysUser)request.getSession().getAttribute("userInfo");
        // 设置用户名
        slog.setUsername(user.getUsername());

        // 当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        // 设置操作时间
        slog.setOperateTime(now);

        // 设置当前 IP
        // request.getRemoteAddr()
        slog.setIpAddr(IpUtil.getIpAddr(request));

        // 获取 User Agent 信息
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 设置浏览器名称+版本
        slog.setBrowserType(userAgent.getBrowser().toString() + "-" + userAgent.getBrowserVersion());
        // 设置操作系统
        slog.setOsType(userAgent.getOperatingSystem().toString());

        // 获取操作的信息
        String[] arrInfo = getCustomAttributes(point);
        // 设置操作类型
        slog.setOperateType(arrInfo[0]);
        // 设置操作说明
        slog.setOperateDesc(arrInfo[1]);

        // 获取目标方法的返回值
        // 目标方法本身的
        Object proceed = point.proceed();
        // 转换为 JSON 字符串
        String strInfo = (String) proceed;

        // 操作详情
        Object[] args = point.getArgs();
        String strOperateType = slog.getOperateType();
        if(strOperateType.contains("查询") || strOperateType.contains("其他")){
            slog.setOperateDetail(JSON.toJSONString(args[0]));
            // 保存
            slService.save(slog);
        }

        if(strOperateType.contains("登录")){
            slog.setOperateDetail("");
            // 保存
            slService.save(slog);
        }

        if(strOperateType.contains("新增") || strOperateType.contains("保存") || strOperateType.contains("添加")){
            // 过滤掉实体中的非基本属性（对象、集合等）
            Map<String, Object> map = FilterEntityUtil.getKeyAndValue(args[0]);
            slog.setOperateDetail(JSON.toJSONString(map));
            // 保存
            slService.save(slog);
        }

        if(strOperateType.contains("删除") || strOperateType.contains("修改")){
            slog.setOperateDetail(strInfo);
            // 保存
            slService.save(slog);
        }

        return proceed;
    }
}
