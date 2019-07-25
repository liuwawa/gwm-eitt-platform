package com.cloud.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.model.log.Sms;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;
import java.util.Map;

@Mapper
public interface SmsDao extends BaseMapper<Sms> {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into t_sms(phone, signName, templateCode, params, day, createTime, updateTime) "
			+ "values(#{phone}, #{signName}, #{templateCode}, #{params}, #{day}, #{createTime}, #{updateTime})")
	int save(Sms sms);

//	@Select("select * from t_sms t where t.id = #{id}")
//	Sms findById(Long id);

	int update(Sms sms);

	int count(Map<String, Object> params);

	List<Sms> findData(Map<String, Object> params);

	@Delete("DELETE FROM t_sms")
	void delAllSms();
}
