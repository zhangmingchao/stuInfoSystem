package com.lc.demo.service.impl;

import com.lc.demo.bean.StuBook;
import com.lc.demo.mapper.StuBookMapper;
import com.lc.demo.service.StuBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuBookServiceImpl implements StuBookService {

    @Autowired
    private StuBookMapper stuBookMapper;
    @Override
    public StuBook getByStuIdAndSubName(String stuId, String subName) {
        return stuBookMapper.getByStuIdAndSubName(stuId,subName);
    }

    @Override
    public void save(StuBook stuBook) {
        stuBookMapper.save(stuBook);
    }

    @Override
    public void updateById(StuBook exit) {
        stuBookMapper.updateById(exit);
    }
}
