package com.gwm.one.hr.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwm.one.model.hr.user.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, sex, groupId, enabled, type, createTime, updateTime) "
            + "values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{sex},#{groupId},#{enabled}, #{type}, #{createTime}, #{updateTime})")
    int save(SysUser appUser);

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

    @Select("select max((substring(personnelNO, 3))+0) from sys_user")
    Integer selectMaxPersonnelNO();

    Integer updateGroupIdById(@Param("id") Long id, @Param("groupId") Integer groupId);

    List<SysUser> selectPageExt(@Param("p") Page<SysUser> p, @Param("groupIds") List<Integer> groupIds, @Param("roleType") List<String> roleType, @Param("user") SysUser user);
}
