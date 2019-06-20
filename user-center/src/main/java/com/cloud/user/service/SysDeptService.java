package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.user.SysDept;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {

    /**
     * 多条件查询
     */
    List<SysDept> findData(SysDept sysDept);

    /**
     * 查询子部门
     */
    List<SysDept> selectByParentId(Long parentId);

    /**
     * 动态增加
     */
    void insertDept(SysDept sysDept);

    /**
     * 删除
     */
    int deletById(Long id);

    /**
     * 修改
     */
    void updateSysDept(SysDept sysDept);
}
