package com.cloud.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.backend.model.BlackIP;
import com.cloud.model.common.Page;

import java.util.Map;

public interface BlackIPService extends IService<BlackIP> {

    void saveBlackIp(BlackIP blackIP);

    void delete(Integer ip);

    Page<BlackIP> findBlackIPs(Map<String, Object> params);

    void deleteAll();

    void delete(String ip);
}
