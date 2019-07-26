package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.user.SysGroupGrouping;

import java.util.List;

/**
 * <p>
 * 分组组织中间表 服务类
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */

public interface SysGroupGroupingService extends IService<SysGroupGrouping> {
    /**
     * @param groupIds   组织id
     * @param groupingId 分组id
     * @return 保存是否成功
     * 保存多个组织到一个分组里
     */
    boolean saveGroupToGrouping(List<Integer> groupIds, Integer groupingId, String groupingName,
                                String groupingRemark, String loginAdminName);
}
