package com.lc.demo.bean;

public class StuBook {

    private String stuId;

    private String subName;

    private String content;

    public StuBook(String stuId, String subName, String content) {
        this.stuId = stuId;
        this.subName = subName;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                "stuId='" + stuId + '\'' +
                ", subName='" + subName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
