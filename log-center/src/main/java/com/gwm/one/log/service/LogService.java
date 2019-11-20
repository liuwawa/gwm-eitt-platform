package com.gwm.one.log.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwm.one.model.common.Page;
import com.gwm.one.model.log.Log;

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
