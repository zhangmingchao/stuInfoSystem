package com.lc.demo.bean;

import java.util.Objects;

public class Subject {

    private String subId;

    private String subName;

    private String subTerm;

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubTerm() {
        return subTerm;
    }

    public void setSubTerm(String subTerm) {
        this.subTerm = subTerm;
    }

    public Subject(String subId, String subName, String subTerm) {
        this.subId = subId;
        this.subName = subName;
        this.subTerm = subTerm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(subId, subject.subId) && Objects.equals(subName, subject.subName) && Objects.equals(subTerm, subject.subTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subId, subName, subTerm);
    }

    public Subject() {
    }
}
