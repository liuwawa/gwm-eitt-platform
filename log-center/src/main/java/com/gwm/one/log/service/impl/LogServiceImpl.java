package com.gwm.one.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwm.one.common.utils.PageUtil;
import com.gwm.one.log.dao.LogDao;
import com.gwm.one.log.service.LogService;
import com.gwm.one.model.common.Page;
import com.gwm.one.model.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

//@Primary
@Service
public class LogServiceImpl extends ServiceImpl<LogDao, Log> implements LogService {

    @Autowired
    private LogDao logDao;

    /**
     * 将日志保存到数据库<br>
     * 注解@Async是开启异步执行
     *
     * @param log
     */
    @Async
    @Override
    public void saveLog(Log log) {
        if (log.getFlag() == null) {
            log.setFlag(Boolean.TRUE);
        }
        if (log.getCreateTime() == null) {
            log.setCreateTime(new Date());
        }
        if (log.getUserid() == null) {
            log.setUserid(0);
        }

        logDao.saveLog(log);
    }

    @Override
    public Page<Log> findLogs(Map<String, Object> params) {
        List<Log> list = Collections.emptyList();
        int total = logDao.count(params);
        if (total > 0) {
            PageUtil.pageParamConver(params, true);
            list = logDao.findData(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public void delAllLog() {
        logDao.delAllLog();
    }
}
