package com.cloud.personnel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.model.personnel.BxPersonnel;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 个人保险信息表 Mapper 接口
 * </p>
 *
 * @author liuek
 * @since 2019-07-25
 */
@Mapper
public interface BxPersonnelDao extends BaseMapper<BxPersonnel> {

}
