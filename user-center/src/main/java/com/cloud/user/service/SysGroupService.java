package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.user.GroupWithExpand;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGroupExpand;

/**
 * <p>
 * 组织表 服务类
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
public interface SysGroupService extends IService<SysGroup> {
    /**
     * @param sysGroup       组织主表数据
     * @param sysGroupExpand 组织拓展表数据
     * @return 操作结果
     * 保存组织信息和其拓展信息
     */
    boolean saveGroupAndGroupExpand(SysGroup sysGroup, SysGroupExpand sysGroupExpand);

    /**
     * @param groupId 组织的主键
     * @return 组织的完整信息
     * 根据id查找出整个组织的详细信息
     */
    GroupWithExpand selectByGroupId(Integer groupId);
}
