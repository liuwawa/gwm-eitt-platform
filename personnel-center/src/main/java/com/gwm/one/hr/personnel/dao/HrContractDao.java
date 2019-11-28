package com.gwm.one.hr.personnel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gwm.one.model.hr.personnel.HrContract;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 合同信息表 Mapper 接口
 * </p>
 *
 * @author liuek
 * @since 2019-07-25
 */
@Mapper
public interface HrContractDao extends BaseMapper<HrContract> {

}
