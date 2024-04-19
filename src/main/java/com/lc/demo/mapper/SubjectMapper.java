package com.lc.demo.mapper;

import com.lc.demo.bean.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubjectMapper {

    @Select("select distinct sub_id as subId,sub_name as subName,sub_term as subTerm from subject")
    List<Subject> findList();
}
