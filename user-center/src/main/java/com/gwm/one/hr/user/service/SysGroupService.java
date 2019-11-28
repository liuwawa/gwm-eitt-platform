package com.gwm.one.hr.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwm.one.model.hr.user.SysGroup;
import com.gwm.one.model.hr.user.SysGroupExpand;

import java.util.List;

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
     * @param sysGroup       组织主表数据
     * @param sysGroupExpand 组织拓展表数据
     * @return 操作结果
     * 编辑组织信息和其拓展信息
     */
    boolean updateGroupAndGroupExpand(SysGroup sysGroup, SysGroupExpand sysGroupExpand);


    /**
     * @param groupIds       需要删除的组织id
     * @param loginAdminName 当前操作人
     * @return 操作结果
     * 逻辑批量删除组织
     */
    boolean updateByIds(List<Integer> groupIds, String loginAdminName);

    /**
     * @param groupIds 需要移动的组织id
     * @param parentId 移动到哪个组织下的id
     * @return 操作结果
     * 把一个组织结构移动到另一个组织上
     */
    boolean changeGroup(List<Integer> groupIds, Integer parentId, String loginAdminName);
}
