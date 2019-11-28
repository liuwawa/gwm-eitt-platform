package com.gwm.one.hr.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gwm.one.model.hr.user.SysGroupGrouping;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 分组组织中间表 Mapper 接口
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */
@Mapper
public interface SysGroupGroupingDao extends BaseMapper<SysGroupGrouping> {
    boolean insertList(List<SysGroupGrouping> sysGroupGroupingList);
}
