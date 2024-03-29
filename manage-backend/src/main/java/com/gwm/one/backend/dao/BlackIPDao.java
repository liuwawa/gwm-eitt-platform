package com.gwm.one.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.gwm.one.backend.model.BlackIP;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlackIPDao extends BaseMapper<BlackIP> {

	@Insert("insert into black_ip(ip, createTime) values(#{ip}, #{createTime})")
	int save(BlackIP blackIP);

	@Delete("delete from black_ip where id = #{id}")
	int delete(Integer id);

	@Delete("delete from black_ip where ip = #{ip}")
	int delete(String ip);

	@Delete("delete from black_ip")
	int deleteAll();

	int count(Map<String, Object> params);

	List<BlackIP> findData(Map<String, Object> params);
}
