package com.lc.demo.controller;

import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeader {

    /**
     * 文件名称
     * @return
     */
    String fileName() default "导出文件";

    /**
     * sheet页名称
     * @return
     */
    String sheetName() default "sheet1";

    /**
     * 表头是否加粗
     * @return
     */
    boolean headBold() default false;

    /**
     * 表头颜色
     * @return
     */
    IndexedColors headColor() default IndexedColors.GREEN;
}
