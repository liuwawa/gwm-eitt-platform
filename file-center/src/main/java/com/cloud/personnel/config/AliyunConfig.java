package com.gwm.one.personnel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.OSSClient;

/**
 * 阿里云配置
 * 
 * @author lz
 *
 */
@Configuration
public class AliyunConfig {

	@Value("${personnel.aliyun.endpoint}")
	private String endpoint;
	@Value("${personnel.aliyun.accessKeyId}")
	private String accessKeyId;
	@Value("${personnel.aliyun.accessKeySecret}")
	private String accessKeySecret;

	/**
	 * 阿里云文件存储client
	 * 
	 */
	@Bean
	public OSSClient ossClient() {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		return ossClient;
	}

}
