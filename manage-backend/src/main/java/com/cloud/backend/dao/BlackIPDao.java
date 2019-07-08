package com.cloud.backend.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cloud.backend.model.BlackIP;

@Mapper
public interface BlackIPDao extends BaseMapper<BlackIP> {

	@Insert("insert into black_ip(ip, createTime) values(#{ip}, #{createTime})")
	int save(BlackIP blackIP);

	@Delete("delete from black_ip where id = #{id}")
	int delete(Integer id);

	@Delete("delete from black_ip where ip = #{ip}")
	int delete(String ip);

	@Select("select * from black_ip t where t.ip = #{ip}")
	BlackIP findByIp(String ip);

	int count(Map<String, Object> params);

	List<BlackIP> findData(Map<String, Object> params);

	@Delete("delete from black_ip")
	int deleteAll();
}
