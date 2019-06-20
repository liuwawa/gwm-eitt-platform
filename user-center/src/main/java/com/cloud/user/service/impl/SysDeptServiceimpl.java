package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.model.user.SysDept;
import com.cloud.user.dao.SysDeptDao;
import com.cloud.user.service.SysDeptService;
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

    @Override
    public List<SysDept> selectByParentId(Long parentId) {
        return sysDeptDao.selectByParentId(parentId);
    }

    @Override
    public void insertDept(SysDept sysDept) {
        sysDeptDao.insertDept(sysDept);
    }

    @Override
    public int deletById(Long id) {
        return sysDeptDao.deletById(id);
    }

    @Override
    public void updateSysDept(SysDept sysDept) {
        sysDeptDao.updateSysDept(sysDept);
    }
}
