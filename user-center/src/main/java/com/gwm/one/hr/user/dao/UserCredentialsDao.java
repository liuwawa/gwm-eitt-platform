package com.gwm.one.hr.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gwm.one.model.hr.user.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.gwm.one.model.hr.user.UserCredential;

@Mapper
public interface UserCredentialsDao extends BaseMapper<UserCredential> {

	@Insert("insert into user_credentials(username, type, userId) values(#{username}, #{type}, #{userId})")
	int save(UserCredential userCredential);

	@Select("select * from user_credentials t where t.username = #{username}")
	UserCredential findByUsername(String username);

	@Select("select u.* from sys_user u inner join user_credentials c on c.userId = u.id where c.username = #{username}")
    SysUser findUserByUsername(String username);
}
