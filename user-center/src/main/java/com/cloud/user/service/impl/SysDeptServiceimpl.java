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


}
