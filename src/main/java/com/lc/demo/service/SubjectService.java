package com.lc.demo.service;

import com.lc.demo.bean.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectService {

    List<Subject> findList();

    Map<String, Object> findListById(String stuId, String subName);
}
