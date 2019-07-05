package com.cloud.backend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.backend.dao.BlackIPDao;
import com.cloud.backend.model.BlackIP;
import com.cloud.backend.service.BlackIPService;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.common.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BlackIPServiceImpl extends ServiceImpl<BlackIPDao, BlackIP> implements BlackIPService {

    @Autowired
    private BlackIPDao blackIPDao;

    @Transactional
    @Override
    public void saveBlackIp(BlackIP blackIP) {
        blackIPDao.save(blackIP);
        log.info("添加黑名单ip:{}", blackIP);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        int n = blackIPDao.delete(id);
        if (n > 0) {
            log.info("删除黑名单ip:{}", id);
        }
    }




	@Override
	public Page<BlackIP> findBlackIPs(Map<String, Object> params) {
		int total = blackIPDao.count(params);
		List<BlackIP> list = Collections.emptyList();
		if (total > 0) {
			PageUtil.pageParamConver(params, false);

			list = blackIPDao.findData(params);
		}
		return new Page<>(total, list);
	}

    @Override
    @Transactional
    public void deleteAll() {
        blackIPDao.deleteAll();
    }
}
