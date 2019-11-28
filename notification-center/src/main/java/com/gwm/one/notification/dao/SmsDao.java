package com.gwm.one.notification.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import com.gwm.one.notification.model.Sms;

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
