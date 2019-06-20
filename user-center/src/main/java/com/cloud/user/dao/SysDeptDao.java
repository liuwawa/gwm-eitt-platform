package com.cloud.user.dao;

import com.cloud.model.user.SysDept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysDeptDao {

    @Options(useGeneratedKeys = true, keyProperty = "deptId")
    @Insert("INSERT INTO  sys_dept(parent_id,dept_name,leader,phone,email,create_time) VALUES"+
           "(#{parentId},#{deptName},#{leader},#{phone},#{email},#{createTime})")
    int saveDept(SysDept sysDept);


    /**查询子级部门*/
    @Select("SELECT * FROM sys_dept t WHERE t.parent_id =#{parentId}")
    List<SysDept> selectByParentId(Long parentId);

    /** 多条件查询*/
    List<SysDept> findData(SysDept sysDept);

    /**动态增加*/
    void insertDept(SysDept sysDept);

    @Delete("delete from sys_dept where dept_id = #{id}")
    int deletById(Long id);

    void updateSysDept(SysDept sysDept);
}