package com.gwm.one.log.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gwm.one.model.log.constants.LogQueue;

/**
 * rabbitmq配置
 * 
 * @author lz
 *
 */
@Configuration
public class RabbitmqConfig {

	/**
	 * 声明队列
	 * 
	 * @return
	 */
	@Bean
	public Queue logQueue() {
		Queue queue = new Queue(LogQueue.LOG_QUEUE);

		return queue;
	}
}
