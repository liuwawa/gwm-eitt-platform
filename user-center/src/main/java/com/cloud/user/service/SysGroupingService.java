package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.user.SysGrouping;

import java.util.List;


/**
 * <p>
 * 组织分组表 服务类
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
public interface SysGroupingService extends IService<SysGrouping> {

    boolean updateByIds(List<Integer> groupingIds, String loginAdminName);

    boolean initGroupingSaveGroup(List<Integer> list, SysGrouping grouping);

}
