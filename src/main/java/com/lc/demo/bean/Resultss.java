package com.lc.demo.bean;

//import com.alibaba.excel.annotation.ExcelIgnore;
//import com.alibaba.excel.annotation.ExcelProperty;

import com.lc.demo.controller.ExcelField;
import com.lc.demo.controller.ExcelHeader;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @ClassName Result
 * @Deacription TODO
 * @Author daier
 * @Date 2021/1/9 11:24
 * @Version 1.0
 **/
@ExcelHeader(fileName = "导出文件.xlsx")
public class Resultss implements Serializable {
    private int resId;
//    @ExcelProperty("学号")
@ExcelField(name = "学号", width = 500, order = 1)
    private String stuId;
//    @ExcelProperty("课程名")
@ExcelField(name = "课程名", width = 500, order = 2)
    private String subName;

    @Max(value = 100,message = "成绩最大值不能超过100")
    @Min(value = 0,message = "成绩最小值不能小于0")
//    @ExcelProperty("成绩")
    @ExcelField(name = "成绩", width = 500, order = 3)
    private int    resNum;
//    @ExcelProperty("学期")
@ExcelField(name = "学期", width = 500, order = 4)
    private String resTerm;

    public Resultss() {
    }

    public Resultss(int resId, String stuId, String subName, int resNum, String resTerm) {
        this.resId = resId;
        this.stuId = stuId;
        this.subName = subName;
        this.resNum = resNum;
        this.resTerm = resTerm;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getResNum() {
        return resNum;
    }

    public void setResNum(int resNum) {
        this.resNum = resNum;
    }

    public String getResTerm() {
        return resTerm;
    }

    public void setResTerm(String resTerm) {
        this.resTerm = resTerm;
    }

    @Override
    public String toString() {
        return "{" +
                "resId=" + resId +
                ", stuId='" + stuId + '\'' +
                ", subName='" + subName + '\'' +
                ", resNum=" + resNum +
                ", resTerm='" + resTerm + '\'' +
                '}';
    }
}
