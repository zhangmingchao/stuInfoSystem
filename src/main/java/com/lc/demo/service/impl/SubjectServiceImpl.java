package com.lc.demo.service.impl;

import com.lc.demo.bean.Subject;
import com.lc.demo.mapper.SubjectMapper;
import com.lc.demo.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubjectServiceImpl  implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<Subject> findList() {
        List<com.lc.demo.bean.Subject> list = subjectMapper.findList();
        return list;
    }

    @Override
    public Map<String, Object> findListById(String stuId, String subName) {
        return null;
    }
}
