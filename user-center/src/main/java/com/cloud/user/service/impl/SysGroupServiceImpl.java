package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.exception.ResultException;
import com.cloud.model.user.GroupWithExpand;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGroupExpand;
import com.cloud.user.dao.SysGroupDao;
import com.cloud.user.service.SysGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 组织表 服务实现类
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@Service
@Slf4j
public class SysGroupServiceImpl extends ServiceImpl<SysGroupDao, SysGroup> implements SysGroupService {

    @Override
    @Transactional
    public boolean saveGroupAndGroupExpand(SysGroup sysGroup, SysGroupExpand sysGroupExpand) {
        // 非空验证
        if (StringUtils.isBlank(sysGroup.getGroupName())) {
            log.error("添加组织的名字为空值!");
            throw new ResultException(ResultEnum.GROUPNAME_NULL.getCode(),
                    ResultEnum.GROUPNAME_NULL.getMessage());
        }
        sysGroup.setCreateBy(sysGroup.getLoginAdminName());
        sysGroup.setCreateTime(new Date());
        // 先添加group主表
        boolean groupSave = sysGroup.insert();
        // 给expand拓展表添加外键groupId
        sysGroupExpand.setGroupId(sysGroup.getGroupId());
        // 设置全称(路径)
        sysGroupExpand.setGFullname(sysGroupExpand.getUnitName() + sysGroupExpand.getDeptName() + sysGroupExpand.getTeamName());

        // 再添加groupExpand拓展表
        boolean groupExpandSave = sysGroupExpand.insert();

        return groupSave && groupExpandSave;
    }

    @Override
    @Transactional
    public GroupWithExpand selectByGroupId(Integer groupId) {
        // 非空验证
        if (null == groupId) {
            log.error("根据id查找组织时，传入了空的id值");
            throw new ResultException(ResultEnum.GROUPID_NULL.getCode(),
                    ResultEnum.GROUPID_NULL.getMessage());
        }
        // 构建组织主表对象，填充数据
        SysGroup sysGroup = SysGroup.builder().build();
        SysGroup group = sysGroup.selectOne(new QueryWrapper<SysGroup>().lambda()
                .eq(SysGroup::getIsDel, 0)
                .eq(SysGroup::getGroupId, groupId));
        // 判断是否存在该组织
        if (null == group) {
            log.error("根据id查找组织时，组织主表中不存在该id的组织，或者已经删除，id:{}", groupId);
            throw new ResultException(ResultEnum.GROUP_NOT_EXIST.getCode(),
                    ResultEnum.GROUP_NOT_EXIST.getMessage());
        }
        // 计算子节点
        Integer childCount = sysGroup.selectCount(new QueryWrapper<SysGroup>().lambda().
                eq(SysGroup::getIsDel, "0")
                .eq(SysGroup::getGroupParentId, group.getGroupId()));
        group.setGroupChildCount(childCount);
        // 每次查询对子节点数量进行更新
        group.updateById();
        // 构建组织拓展对象，填充数据
        SysGroupExpand sysGroupExpand = SysGroupExpand.builder().build();
        SysGroupExpand groupExpand = sysGroupExpand.selectOne(new QueryWrapper<SysGroupExpand>().eq("groupId", groupId));

        // 构建组织对象，并返回
        return new GroupWithExpand(group, groupExpand);
    }

    @Override
    @Transactional
    public boolean updateGroupAndGroupExpand(SysGroup sysGroup, SysGroupExpand sysGroupExpand) {
        // 非空验证
        if (null == sysGroup.getGroupId()) {
            log.error("修改组织的id为空值!");
            throw new ResultException(ResultEnum.GROUPID_NULL.getCode(),
                    ResultEnum.GROUPID_NULL.getMessage());
        }

        // 先修改group主表
        boolean groupSave = sysGroup.updateById();
        // 查找该对象
        SysGroupExpand groupExpandBySelect = sysGroupExpand.selectOne(new QueryWrapper<SysGroupExpand>().lambda()
                .eq(SysGroupExpand::getGroupId, sysGroup.getGroupId()));
        // 设置全称
        String gFullname = getGFullname(sysGroupExpand, groupExpandBySelect);
        sysGroupExpand.setGFullname(gFullname);
        // 再修改groupExpand拓展表
        boolean groupExpandSave = sysGroupExpand.update(new QueryWrapper<SysGroupExpand>().lambda()
                .eq(SysGroupExpand::getGroupId, sysGroup.getGroupId()));

        return groupSave && groupExpandSave;
    }

    // 设置全称
    private static String getGFullname(SysGroupExpand sysGroupExpand, SysGroupExpand sysGroupExpand2) {
        String unitName = sysGroupExpand.getUnitName();
        String deptName = sysGroupExpand.getDeptName();
        String teamName = sysGroupExpand.getTeamName();

        String unitName1 = sysGroupExpand2.getUnitName();
        String deptName1 = sysGroupExpand2.getDeptName();
        String teamName1 = sysGroupExpand2.getTeamName();

        if (null == unitName && null != deptName && null != teamName) {
            return new StringBuilder().append(unitName1).append(deptName).append(teamName).toString();
        }
        if (null == deptName && null != unitName && null != teamName) {
            return new StringBuilder().append(unitName).append(deptName1).append(teamName).toString();
        }
        if (null == teamName && null != unitName && null != deptName) {
            return new StringBuilder().append(unitName).append(deptName).append(teamName1).toString();
        }
        if (null == unitName && null == deptName && null != teamName) {
            return new StringBuilder().append(unitName1).append(deptName1).append(teamName).toString();
        }
        if (null == deptName && null != unitName && null == teamName) {
            return new StringBuilder().append(unitName).append(deptName1).append(teamName1).toString();
        }
        if (null == teamName && null == unitName && null != deptName) {
            return new StringBuilder().append(unitName1).append(deptName).append(teamName1).toString();
        }
        return new StringBuilder().append(unitName1).append(deptName1).append(teamName1).toString();
    }

    @Override
    @Transactional
    public boolean updateByIds(List<Integer> groupIds, String loginAdminName) {
        if (null == groupIds || groupIds.size() == 0) {
            log.error("逻辑批量删除组织,获取到的组织id都为空值");
            throw new ResultException(ResultEnum.GROUPID_NULL.getCode(),
                    ResultEnum.GROUPID_NULL.getMessage());
        }
        // 构建对象
        SysGroup sysGroup = SysGroup.builder().isDel("1")
                .deleteBy(loginAdminName).deleteTime(new Date()).build();
        // 进行修改操作
        for (Integer groupId : groupIds) {
            sysGroup.setGroupId(groupId);
            log.info("删除的组织id:{}", groupId);
            if (!sysGroup.updateById()) {
                throw new ResultException(ResultEnum.GROUP_NOT_EXIST.getCode(),
                        ResultEnum.GROUP_NOT_EXIST.getMessage() + ",组织id:" + groupId);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(SysGroup sysGroup) {
        // 非空验证
        if (null == sysGroup.getGroupId()) {
            log.error("删除分组,获取到的分组id为空值");
            throw new ResultException(ResultEnum.GROUPINGID_NULL.getCode(),
                    ResultEnum.GROUPINGID_NULL.getMessage());
        }
        // 赋值删除人，删除时间，删除标识
        sysGroup.setDeleteTime(new Date());
        sysGroup.setDeleteBy(sysGroup.getLoginAdminName());
        sysGroup.setIsDel("1");

        return sysGroup.updateById();
    }
}
