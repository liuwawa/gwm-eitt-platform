package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.constants.SysConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.exception.ResultException;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.user.*;
import com.cloud.user.dao.SysGroupGroupingDao;
import com.cloud.user.dao.SysGroupingDao;
import com.cloud.user.service.SysGroupingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织分组表 服务实现类
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@Service
@Slf4j
public class SysGroupingServiceImpl extends ServiceImpl<SysGroupingDao, SysGrouping> implements SysGroupingService {
    @Autowired
    private SysGroupGroupingDao sysGroupGroupingDao;

    @Override
    @Transactional
    public boolean updateByIds(List<Integer> groupingIds, String loginAdminName) {
        // 非空验证
        if (null == groupingIds || groupingIds.size() == 0) {
            log.error("逻辑批量删除分组,获取到的分组id都为空值");
            throw new ResultException(ResultEnum.GROUPINGID_NULL.getCode(),
                    ResultEnum.GROUPINGID_NULL.getMessage());
        }

        //  查看是否有用户使用分组
        SysUserGrouping userGrouping = SysUserGrouping.builder().build();
        List<SysUserGrouping> userGroupings = userGrouping.selectList(new QueryWrapper<SysUserGrouping>().lambda()
                .in(SysUserGrouping::getGroupingId, groupingIds));
        if (null != userGroupings && userGroupings.size() != 0) {
            throw new ResultException(500, "用户使用分组中，请先解除关系！");
        }
        // 删除中间表的数据
        SysGroupGrouping sysGroupGrouping = SysGroupGrouping.builder().build();
        sysGroupGrouping.delete(new QueryWrapper<SysGroupGrouping>().lambda().in(SysGroupGrouping::getGroupingId, groupingIds));
        // 构建对象
        SysGrouping sysGrouping = SysGrouping.builder().isDel("1")
                .deleteBy(loginAdminName).deleteTime(new Date()).build();
        // 进行修改操作
        for (Integer groupingId : groupingIds) {
            sysGrouping.setGroupingId(groupingId);
            log.info("删除的分组id:{}", groupingId);
            if (!sysGrouping.updateById()) {
                throw new ResultException(ResultEnum.GROUPING_NOT_EXIST.getCode(),
                        ResultEnum.GROUPING_NOT_EXIST.getMessage() + ",分组id:" + groupingId);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean initGroupingSaveGroup(List<Integer> list, SysGrouping grouping) {
        // 查重
        SysGrouping sysGrouping = grouping.selectOne(new QueryWrapper<SysGrouping>()
                .lambda().eq(SysGrouping::getGroupingName, grouping.getGroupingName()));
        if (null != sysGrouping) {
            if ("0".equals(sysGrouping.getIsDel())) {
                throw new ResultException(500, "分组名称重复！"); // 该数据存在，返回重复
            } else {
                sysGrouping.deleteById(); // 已经被删除，物理删除
            }
        }

        // 添加一个分组
        grouping.insert();
        // 构建对象
        SysGroupGrouping sysGroupGrouping = SysGroupGrouping.builder().groupingId(grouping.getGroupingId()).build();
        // 先删除存在的，避免插入失败
        sysGroupGrouping.delete(new QueryWrapper<SysGroupGrouping>()
                .lambda().eq(SysGroupGrouping::getGroupingId, sysGroupGrouping.getGroupingId()));

        List<SysGroupGrouping> groupGroupings = new ArrayList<>();
        list.forEach(groupId -> {
            SysGroupGrouping groupGrouping = SysGroupGrouping.builder().build();
            groupGrouping.setGroupingId(sysGroupGrouping.getGroupingId());
            groupGrouping.setGroupId(groupId);
            groupGroupings.add(groupGrouping);
        });
        // 添加到中间表
        sysGroupGroupingDao.insertList(groupGroupings);

        // 获取当前用户的权限，如果不是超级管理员，直接放入自己可以管理的分组
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Set<SysRole> sysRoles = loginAppUser.getSysRoles();
        List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
        if (!(roleIds.contains(SysConstants.ADMIN_ROLE_ID))) {
            Long id = loginAppUser.getId();
            SysUserGrouping userGrouping = SysUserGrouping.builder().userId(id.intValue()).groupingId(grouping.getGroupingId()).build();
            userGrouping.insert();
        }

        return true;
    }

    @Override
    public List<SysGrouping> findAllCheckedGrouping() {

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Set<SysRole> sysRoles = loginAppUser.getSysRoles();
        List<Long> list = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
        List<SysGrouping> groupings = new ArrayList<>();
        // 找出所有的分组
        SysGrouping grouping = SysGrouping.builder().build();
        List<SysGrouping> allGroupings = grouping.selectList(new QueryWrapper<SysGrouping>().lambda().eq(SysGrouping::getIsDel, 0));
        if (!(list.contains(SysConstants.ADMIN_ROLE_ID))) {
            SysUserGrouping userGrouping = SysUserGrouping.builder().build();
            List<SysUserGrouping> sysUserGroupings = userGrouping.selectList(
                    new QueryWrapper<SysUserGrouping>().lambda().eq(SysUserGrouping::getUserId, loginAppUser.getId().intValue()));
            List<Integer> collect = sysUserGroupings.stream().map(SysUserGrouping::getGroupingId).collect(Collectors.toList());
            SysGrouping sysGrouping = SysGrouping.builder().build();
            groupings = sysGrouping.selectList(
                    new QueryWrapper<SysGrouping>().lambda().in(SysGrouping::getGroupingId, collect).eq(SysGrouping::getIsDel, 0)
                            .orderByAsc(SysGrouping::getGroupingShowOrder));
            return groupings;
        }
        return allGroupings;
    }
}
