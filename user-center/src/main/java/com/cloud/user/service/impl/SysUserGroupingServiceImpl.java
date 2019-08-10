package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.constants.SysConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.exception.ResultException;
import com.cloud.model.user.*;
import com.cloud.user.dao.SysUserGroupingDao;
import com.cloud.user.service.SysRoleService;
import com.cloud.user.service.SysUserGroupingService;
import com.cloud.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户，分组中间表 服务实现类
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */
@Service
@Slf4j
public class SysUserGroupingServiceImpl extends ServiceImpl<SysUserGroupingDao, SysUserGrouping> implements SysUserGroupingService {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;

    @Override
    @Transactional
    public boolean saveUserCheck(List<Integer> groupingIds, Integer userId) {
        // 非空验证
        if (null == userId) {
            log.error("添加用户可以查看的分组,获取到的用户id为空值");
            throw new ResultException(ResultEnum.USERID_ISNULL.getCode(),
                    ResultEnum.USERID_ISNULL.getMessage());
        }
        SysUserGrouping sysUserGrouping = SysUserGrouping.builder().userId(userId).build();
        // 先进行删除
        sysUserGrouping.delete(new QueryWrapper<SysUserGrouping>().lambda().eq(SysUserGrouping::getUserId, userId));
        // 传来空数组证明取消该用户和所有分组的关联关系，直接删除中间表中的数据并返回true
        if (groupingIds.size() == 0 || groupingIds == null) {
            return true;
        }
        // 添加
        for (Integer groupingId : groupingIds) {
            sysUserGrouping.setGroupingId(groupingId);
            if (!sysUserGrouping.insert()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<SysGrouping> getGroupingsByUserId(Integer userId) {
        // 非空验证
        if (null == userId) {
            log.error("查看该用户可以查看的分组，获取到的用户id为空值");
            throw new ResultException(ResultEnum.USERID_ISNULL.getCode(),
                    ResultEnum.USERID_ISNULL.getMessage());
        }
        SysGroupGrouping groupGrouping = SysGroupGrouping.builder().build();
        List<SysGroupGrouping> groupGroupings = groupGrouping.selectAll();

        // 用户为超级管理员
        Long l = userId.longValue();
        // 查看当前用户的角色
        Set<SysRole> rolesByUserId = userService.findRolesByUserId(l);
        List<Long> roleIds = new ArrayList<>();
        rolesByUserId.forEach(role -> {
            roleIds.add(role.getId());
        });
        // 当前用户是超级管理员 或者 角色是超级管理员
        if (l.equals(SysConstants.ADMIN_USER_ID) || roleIds.contains(SysConstants.ADMIN_ROLE_ID)) {
            SysGrouping grouping = SysGrouping.builder().build();
            List<SysGrouping> groupings = grouping.selectList(new QueryWrapper<SysGrouping>().lambda()
                    .eq(SysGrouping::getIsDel, "0"));
            getGroups(groupings, groupGroupings);
            return groupings;
        }


        // 构建对象
        SysUserGrouping sysUserGrouping = SysUserGrouping.builder().build();
        // 根据userId查找所有的usergrouping对象
        List<SysUserGrouping> sysUserGroupings = sysUserGrouping.selectList(new QueryWrapper<SysUserGrouping>().lambda()
                .eq(SysUserGrouping::getUserId, userId));
        // 根据groupingIds查找出所有的grouping
        List<Integer> groupingIds = new ArrayList<>();
        sysUserGroupings.forEach(userGrouping -> {
            groupingIds.add(userGrouping.getGroupingId());
        });
        SysGrouping sysGrouping = SysGrouping.builder().build();
        List<SysGrouping> sysGroupings = sysGrouping.selectList(new QueryWrapper<SysGrouping>().lambda()
                .in(SysGrouping::getGroupingId, groupingIds)
                .eq(SysGrouping::getIsDel, "0"));
        if (sysGroupings.size() == 0 || null == sysGroupings) {
            return null;
        }
        getGroups(sysGroupings, groupGroupings);
        return sysGroupings;
    }

    // 该方法找出grouping在groupGrouping中间表中的所有的group，并给每个grouping的children赋值并返回
    public void getGroups(List<SysGrouping> sysGroupings, List<SysGroupGrouping> groupGroupings) {
        List<Integer> groupIds = new ArrayList<>();
        sysGroupings.forEach(grouping -> {
            groupGroupings.forEach(sysGroupGrouping -> {
                if (sysGroupGrouping.getGroupingId().equals(grouping.getGroupingId())) {
                    groupIds.add(sysGroupGrouping.getGroupId());
                }
            });
            SysGroup sysGroup = SysGroup.builder().build();
            if (groupIds.size() != 0 && null != groupIds) {
                List<SysGroup> list = sysGroup.selectList(new QueryWrapper<SysGroup>().lambda()
                        .in(SysGroup::getId, groupIds)
                        .eq(SysGroup::getIsDel, "0"));
                grouping.setChildren(list);
            } else {
                grouping.setChildren(null);
            }
        });
    }
}
