package com.gwm.one.log.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.gwm.one.model.log.Log;

@Mapper
public interface LogDao extends BaseMapper<Log> {

	@Insert("insert into t_log(userid, ip, username, module, params, remark, flag,method,time, createTime) values(#{userid}, #{ip}, #{username}, #{module}, #{params}, #{remark}, #{flag}, #{method},#{time}, #{createTime})")
	int saveLog(Log log);

	int count(Map<String, Object> params);

	List<Log> findData(Map<String, Object> params);

	@Delete("Delete from t_log")
	void delAllLog();
}
