package com.cloud.user.controller;

import com.cloud.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope  // 需要更新的配置类上加@RefreshScope注解，@RefreshScope必须加，否则客户端会收到服务端的更新消息，但是更新不了，因为不知道更新哪里的
public class Test {

    @Value("${spring.datasource.username}")
    private String name;
    @Autowired
    private RedisUtil redisUtil;
    @GetMapping("/users-anon/test")
    public String test() {
        return name;
    }
}