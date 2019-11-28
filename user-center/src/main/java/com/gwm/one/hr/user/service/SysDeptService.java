package com.gwm.one.hr.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwm.one.model.hr.user.SysDept;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {

    /**
     * 多条件查询
     */
    List<SysDept> findData(SysDept sysDept);

}
