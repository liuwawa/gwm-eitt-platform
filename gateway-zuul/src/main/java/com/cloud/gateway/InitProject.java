package com.cloud.gateway;

import com.cloud.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static com.cloud.gateway.config.UserInterceptor.USER_CODE;

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
        redisUtils.delAllKey(USER_CODE);
    }
}