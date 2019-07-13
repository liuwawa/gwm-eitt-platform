package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.exception.ResultException;
import com.cloud.common.utils.PhoneUtil;
import com.cloud.model.user.GroupWithExpand;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGroupExpand;
import com.cloud.model.user.SysUser;
import com.cloud.user.dao.SysGroupDao;
import com.cloud.user.dao.SysUserDao;
import com.cloud.user.service.SysGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    @Transactional
    public boolean saveGroupAndGroupExpand(SysGroup sysGroup, SysGroupExpand sysGroupExpand) {
        // 非空验证
        if (StringUtils.isBlank(sysGroup.getLabel())) {
            log.error("添加组织的名字为空值!");
            throw new ResultException(ResultEnum.GROUPNAME_NULL.getCode(),
                    ResultEnum.GROUPNAME_NULL.getMessage());
        }
        sysGroup.setCreateBy(sysGroup.getLoginAdminName());
        sysGroup.setCreateTime(new Date());
        sysGroup.setEnableTime(new Date());
        // 先添加group主表
        boolean groupSave = sysGroup.insert();
        // 给expand拓展表添加外键groupId
        sysGroupExpand.setGroupId(sysGroup.getId());

        // 设置gGrade
        sysGroupExpand = setGgradeByLevel(sysGroup, sysGroupExpand);

        // 判断新增的组织是哪个级别
        if (!sysGroupExpand.getGGrade().equals("0")) {   //不是集团级别，肯定有父级，是集团级别也不会有这六个属性
            boolean flag = false;
            // 根据其父id查找出其上一级的组织拓展数据
            while (flag) {

                // 根据传来的父id查找出父对象
                SysGroup group = sysGroup.selectOne(new QueryWrapper<SysGroup>().lambda()
                        .eq(SysGroup::getIsDel, "0")
                        .eq(SysGroup::getId, sysGroup.getParentid()));
                // 根据父对象的id查出他的拓展数据
                SysGroupExpand groupExpand = sysGroupExpand.selectOne(new QueryWrapper<SysGroupExpand>().lambda()
                        .eq(SysGroupExpand::getGroupId, group.getId()));
                // 为拓展数据赋值
                sysGroupExpand = setProperty(groupExpand, sysGroupExpand, group);
                // 为sysGroup重新赋值父id， 循环查找
                sysGroup.setParentid(group.getParentid());

                // 直到找到顶级
                if (sysGroupExpand.getUnitId() != null) {
                    flag = true;
                }
            }
        }
        // 再添加groupExpand拓展表
        boolean groupExpandSave = sysGroupExpand.insert();

        return groupSave && groupExpandSave;
    }

    // 根据level设置gGrade
    public static SysGroupExpand setGgradeByLevel(SysGroup sysGroup, SysGroupExpand sysGroupExpand) {
        switch (sysGroup.getLevel()) {
            case 0:
                sysGroupExpand.setGGrade("0");
                break;
            case 1:
                sysGroupExpand.setGGrade("10");
                break;
            case 2:
                sysGroupExpand.setGGrade("20");
                break;
            case 3:
                sysGroupExpand.setGGrade("20");
                break;
            case 4:
                sysGroupExpand.setGGrade("30");
                break;
            case 5:
                sysGroupExpand.setGGrade("30");
                break;
            case 6:
                sysGroupExpand.setGGrade("30");
                break;
        }
        return sysGroupExpand;
    }

    // 设置单位，部门，科室，全称(路径)
    public static SysGroupExpand setProperty(SysGroupExpand sysGroupExpand, SysGroupExpand groupExpand, SysGroup sysGroup) {
        if (sysGroupExpand.getGGrade().equals("20")) {
            groupExpand.setTeamId(sysGroupExpand.getGroupId());
            groupExpand.setTeamName(sysGroup.getLabel());
        }
        if (sysGroupExpand.getGGrade().equals("10")) {
            groupExpand.setDeptId(sysGroupExpand.getGroupId());
            groupExpand.setDeptName(sysGroup.getLabel());
        }
        if (sysGroupExpand.getGGrade().equals("0")) {
            groupExpand.setUnitId(sysGroupExpand.getGroupId());
            groupExpand.setUnitName(sysGroup.getLabel());
        }
        groupExpand.setGFullname(sysGroup.getLabel() + groupExpand.getGFullname());
        return groupExpand;

    }

    @Override
    @Transactional
    public boolean updateGroupAndGroupExpand(SysGroup sysGroup, SysGroupExpand sysGroupExpand) {
        // 非空验证
        if (null == sysGroup.getId()) {
            log.error("修改组织的id为空值!");
            throw new ResultException(ResultEnum.GROUPID_NULL.getCode(),
                    ResultEnum.GROUPID_NULL.getMessage());
        }

        // 先修改group主表
        boolean groupSave = sysGroup.updateById();

        // 设置gGrade
        sysGroupExpand = setGgradeByLevel(sysGroup, sysGroupExpand);

        // 判断新增的组织是哪个级别
        if (!sysGroupExpand.getGGrade().equals("0")) {   //不是集团级别，肯定有父级，是集团级别也不会有这六个属性
            boolean flag = false;
            // 根据其父id查找出其上一级的组织拓展数据
            while (flag) {

                // 根据传来的父id查找出父对象
                SysGroup group = sysGroup.selectOne(new QueryWrapper<SysGroup>().lambda()
                        .eq(SysGroup::getIsDel, "0")
                        .eq(SysGroup::getId, sysGroup.getParentid()));
                // 根据父对象的id查出他的拓展数据
                SysGroupExpand groupExpand = sysGroupExpand.selectOne(new QueryWrapper<SysGroupExpand>().lambda()
                        .eq(SysGroupExpand::getGroupId, group.getId()));
                // 为拓展数据赋值
                sysGroupExpand = setProperty(groupExpand, sysGroupExpand, group);
                // 为sysGroup重新赋值父id，循环查找
                sysGroup.setParentid(group.getParentid());
                // 直到找到顶级
                if (sysGroupExpand.getUnitId() != null) {
                    flag = true;
                }
            }
        }
        // 再修改groupExpand拓展表
        boolean groupExpandSave = sysGroupExpand.update(new QueryWrapper<SysGroupExpand>().lambda()
                .eq(SysGroupExpand::getGroupId, sysGroup.getId()));

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
                .eq(SysGroup::getId, groupId));
        // 判断是否存在该组织
        if (null == group) {
            log.error("根据id查找组织时，组织主表中不存在该id的组织，或者已经删除，id:{}", groupId);
            throw new ResultException(ResultEnum.GROUP_NOT_EXIST.getCode(),
                    ResultEnum.GROUP_NOT_EXIST.getMessage());
        }
        // 计算子节点
        Integer childCount = sysGroup.selectCount(new QueryWrapper<SysGroup>().lambda().
                eq(SysGroup::getIsDel, "0")
                .eq(SysGroup::getParentid, group.getId()));
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
    public boolean updateByIds(List<Integer> groupIds, String loginAdminName) {
        if (null == groupIds || groupIds.size() == 0) {
            log.error("逻辑批量删除组织,获取到的组织id都为空值");
            throw new ResultException(ResultEnum.GROUPID_NULL.getCode(),
                    ResultEnum.GROUPID_NULL.getMessage());
        }

        // 判断该组织是否有用户占用
        List<SysUser> userList = sysUserDao.selectList(new QueryWrapper<>());
        for (SysUser sysUser : userList) {
            if (groupIds.contains(sysUser.getGroupId())) {
                throw new ResultException(500, "删除的组织中有用户占用，删除失败！");
            }
        }
        // 构建对象
        SysGroup sysGroup = SysGroup.builder().isDel("1")
                .deleteBy(loginAdminName).deleteTime(new Date()).build();
        // 进行修改操作
        for (Integer groupId : groupIds) {
            sysGroup.setId(groupId);
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
        if (null == sysGroup.getId()) {
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
