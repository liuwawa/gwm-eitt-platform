package com.gwm.one.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwm.one.backend.model.BlackIP;
import com.gwm.one.model.common.Page;

import java.util.Map;

public interface BlackIPService extends IService<BlackIP> {

    void saveBlackIp(BlackIP blackIP);

    void delete(Integer ip);

    void deleteAll();

    void delete(String ip);

    Page<BlackIP> findBlackIPs(Map<String, Object> params);
}
