package com.lc.demo.service;

import com.lc.demo.bean.StuBook;
import org.apache.ibatis.annotations.Param;

public interface StuBookService {

    StuBook getByStuIdAndSubName(@Param("stuId") String stuId, @Param("subName")String subName);

    void save(StuBook stuBook);

    void updateById(StuBook exit);
}
