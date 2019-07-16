package com.cloud.gateway.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Auther: lz
 * @Description: 保存spring的ApplicationContext容器, 方便其他地方获取Bean
 */
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextHolder.applicationContext = context;
    }

    /**
     * @Auther: lz
     * @Description: 获取 ApplicationContext 容器
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @Auther: lz
     * @Description: 获取 bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}