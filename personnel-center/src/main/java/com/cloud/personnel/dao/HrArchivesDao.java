package com.cloud.personnel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.model.personnel.HrArchives;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 档案信息表 Mapper 接口
 * </p>
 *
 * @author liuek
 * @since 2019-07-25
 */
@Mapper
public interface HrArchivesDao extends BaseMapper<HrArchives> {

}
