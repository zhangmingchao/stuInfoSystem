package com.lc.demo.bean;

import java.io.Serializable;
import java.util.Objects;

public class StuBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

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

    public StuBook(Integer id, String stuId, String subName, String content) {
        this.id = id;
        this.stuId = stuId;
        this.subName = subName;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StuBook{" +
                "id=" + id +
                ", stuId='" + stuId + '\'' +
                ", subName='" + subName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StuBook stuBook = (StuBook) o;
        return Objects.equals(id, stuBook.id) && Objects.equals(stuId, stuBook.stuId) && Objects.equals(subName, stuBook.subName) && Objects.equals(content, stuBook.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stuId, subName, content);
    }

    public StuBook() {
    }
}
