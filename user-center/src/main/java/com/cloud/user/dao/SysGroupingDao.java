package com.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.model.user.SysGrouping;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 组织分组表 Mapper 接口
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@Mapper
public interface SysGroupingDao extends BaseMapper<SysGrouping> {

}
