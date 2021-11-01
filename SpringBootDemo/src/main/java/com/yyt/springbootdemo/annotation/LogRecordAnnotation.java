package com.yyt.springbootdemo.annotation;

import java.lang.annotation.*;

/**
 * @author yyt
 * @date 2021年11月01日 15:51
 * 自定义日志注解，用于记录操作日志
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecordAnnotation {
    // 操作类型
    String operateType() default "";
    // 操作说明，修改详情
    String operateDesc() default "";
}
