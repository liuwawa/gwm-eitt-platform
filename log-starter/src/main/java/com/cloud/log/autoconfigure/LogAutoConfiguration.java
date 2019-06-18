package com.cloud.log.autoconfigure;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.model.log.constants.LogQueue;

@Configuration
public class LogAutoConfiguration {

	/**
	 * 声明队列<br>
	 * 如果日志系统已启动，或者mq上已存在队列 LogQueue.LOG_QUEUE，此处不用声明此队列<br>
	 * 此处声明只是为了防止日志系统启动前，并且没有队列 LogQueue.LOG_QUEUE的情况下丢失消息
	 * 
	 * @return
	 */
	@Bean
	public Queue logQueue() {
		Queue queue = new Queue(LogQueue.LOG_QUEUE);

		return queue;
	}
}
