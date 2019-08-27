package com.cloud.user.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cloud.model.user.SysRole;

@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_role(code, name, createTime, updateTime) values(#{code}, #{name}, #{createTime}, #{createTime})")
	int save(SysRole sysRole);

	@Update("update sys_role t set t.name = #{name} ,t.updateTime = #{updateTime} where t.id = #{id}")
	int update(SysRole sysRole);

	@Select("select * from sys_role t where t.id = #{id}")
	SysRole findById(Long id);

	@Delete("delete from sys_role where id = #{id}")
	int delete(Long id);

	int count(Map<String, Object> params);

	List<SysRole> findData(Map<String, Object> params);
	@Select("select * from sys_role r left join sys_role_user ru on r.id=ru.roleId where ru.userId=#{userId}")
    SysRole findByUserId(Long userId);
}
