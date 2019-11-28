package com.gwm.one.hr.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwm.one.hr.user.service.SysDeptService;
import com.gwm.one.model.hr.user.SysDept;
import com.gwm.one.hr.user.dao.SysDeptDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysDeptServiceimpl extends ServiceImpl<SysDeptDao,SysDept> implements SysDeptService {

    @Autowired
    SysDeptDao sysDeptDao;

    @Override
    public List<SysDept> findData(SysDept sysDept) {
        return sysDeptDao.findData(sysDept);
    }


}
