package com.gwm.one.hr.user.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gwm.one.model.hr.user.constants.UserCenterMq;

/**
 * rabbitmq配置
 * 
 * @author lz
 *
 */
@Configuration
public class RabbitmqConfig {

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(UserCenterMq.MQ_EXCHANGE_USER);
	}
}
