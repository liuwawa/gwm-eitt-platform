package com.cloud.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.cloud.backend.model.BlackIP;

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
}
