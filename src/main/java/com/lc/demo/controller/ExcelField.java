package com.lc.demo.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     * 字段名
     */
    String name() default "";

    /**
     * 字段顺序
     */
    int order() default 1;

    /**
     * 单元格宽度
     */
    int width() default 100;

    /**
     * 日期格式
     */
    String dateFormat() default "yyyy-MM-dd HH:mm:ss";
}
