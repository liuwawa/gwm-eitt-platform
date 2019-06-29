package com.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.model.user.SysUserGrouping;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户，分组中间表 Mapper 接口
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */
@Mapper
public interface SysUserGroupingDao extends BaseMapper<SysUserGrouping> {

}
