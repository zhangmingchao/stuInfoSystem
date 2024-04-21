package com.lc.demo.mapper;

import com.lc.demo.bean.StuBook;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StuBookMapper {

    @Select("select * from stu_book where  stu_id = #{stuId} and sub_name =  #{subName} limit 1")
    StuBook getByStuIdAndSubName(@Param("stuId") String stuId,@Param("subName")String subName);

    @Insert("insert into stu_book (stu_id,sub_name,content) values (#{stuId},#{subName},#{content})")
    void save(StuBook stuBook);

    @Update("update stu_book set content = #{content} where stu_id = #{stuId} and sub_name = #{subName}")
    void updateById(StuBook exit);
}
