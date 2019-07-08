package com.cloud.log.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.common.Page;
import com.cloud.model.log.Log;

public interface LogService extends IService<Log> {

	/**
	 * 保存日志
	 *
	 * @param log
	 */
	void saveLog(Log log);

	Page<Log> findLogs(Map<String, Object> params);

	void  delAllLog();

}
