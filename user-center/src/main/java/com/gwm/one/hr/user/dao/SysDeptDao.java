package com.gwm.one.hr.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gwm.one.model.hr.user.SysDept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysDeptDao extends BaseMapper<SysDept> {

    /**
     * 多条件查询
     */
    List<SysDept> findData(SysDept sysDept);

    /**
     * 动态增加
     */
    void insertDept(SysDept sysDept);

    @Delete("delete from sys_dept where dept_id = #{id}")
    int deletById(Long id);

    /**
     * 修改
     */
    void updateSysDept(SysDept sysDept);
}
