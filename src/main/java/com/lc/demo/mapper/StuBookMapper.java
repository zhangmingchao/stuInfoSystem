package com.lc.demo.mapper;

import com.lc.demo.bean.StuBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StuBookMapper {

    @Select("select * from stu_book where  stu_id = #{stuId} and sub_name =  #{subName} limit 1")
    StuBook getByStuIdAndSubName(@Param("stuId") String stuId,@Param("subName")String subName);

    @Insert("insert into stu_book (stu_id,sub_name,content) values (#{stuId},#{subName},#{content})")
    void save(StuBook stuBook);
}
