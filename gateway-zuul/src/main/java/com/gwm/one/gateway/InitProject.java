package com.gwm.one.gateway;

import com.gwm.one.common.utils.RedisUtils;
import com.gwm.one.gateway.config.UserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 自运行
 */
@Slf4j
@Component
public class InitProject implements ApplicationRunner {
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("运行删除redis数据");
        redisUtils.delAllString(UserInterceptor.USER_CODE);
    }
}