package com.cloud.user.dao;

import java.util.List;
import java.util.Map;

import com.cloud.model.user.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserDao {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, sex, enabled, type, createTime, updateTime) "
			+ "values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{sex}, #{enabled}, #{type}, #{createTime}, #{updateTime})")
	int save(SysUser appUser);

	int update(SysUser appUser);

	/**
	 * @param username
	 * @return
	 */
	@Select("select * from sys_user t where t.username = #{username}")
	SysUser findByUsername(String username);

	@Select("select * from sys_user t where t.phone = #{phone}")
	SysUser findByPhone(String phone);

	/**
	 * @param id
	 * @return
	 */
	@Select("select * from sys_user t where t.id = #{id}")
	SysUser findById(Long id);

	int count(Map<String, Object> params);

	List<SysUser> findData(Map<String, Object> params);

}
