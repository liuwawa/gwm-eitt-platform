package com.gwm.one.hr.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwm.one.model.hr.user.SysGrouping;
import com.gwm.one.model.hr.user.SysUserGrouping;

import java.util.List;

/**
 * <p>
 * 用户，分组中间表 服务类
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */
public interface SysUserGroupingService extends IService<SysUserGrouping> {
    /**
     * @param groupingIds 可以查看的分组的id
     * @param userId      用户的id
     * @return 操作结果
     * 添加用户可以查看的分组
     */
    boolean saveUserCheck(List<Integer> groupingIds, Integer userId);

    /**
     * @param userId 用户的id
     * @return 分组
     * 根据用户id查找出该用户可以查看的分组
     */
    List<SysGrouping> getGroupingsByUserId(Integer userId);
}
