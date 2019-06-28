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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        if (null == sysGroup.getGroupName()) {
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
        // 再添加groupExpand拓展表
        boolean groupExpandSave = sysGroupExpand.insert();

        return groupSave && groupExpandSave;
    }

    @Override
    public GroupWithExpand selectByGroupId(Integer groupId) {
        // 非空验证
        if (null == groupId) {
            log.error("根据id查找组织时，传入了空的id值");
            throw new ResultException(ResultEnum.GROUPID_NULL.getCode(),
                    ResultEnum.GROUPID_NULL.getMessage());
        }
        // 构建组织主表对象，填充数据
        SysGroup sysGroup = SysGroup.builder().build();
        SysGroup group = sysGroup.selectOne(new QueryWrapper<SysGroup>().eq("isDel", 0).eq("groupId", groupId));
        // 判断是否存在该组织
        if (null == group) {
            log.error("根据id查找组织时，组织主表中不存在该id的组织，或者已经删除，id:{}", groupId);
            throw new ResultException(ResultEnum.GROUP_NOT_EXIST.getCode(),
                    ResultEnum.GROUP_NOT_EXIST.getMessage());
        }

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
        if (null == sysGroup.getGroupName()) {
            log.error("修改组织的id为空值!");
            throw new ResultException(ResultEnum.GROUPID_NULL.getCode(),
                    ResultEnum.GROUPID_NULL.getMessage());
        }

        sysGroup.setUpdateBy(sysGroup.getLoginAdminName());
        sysGroup.setUpdateTime(new Date());
        // 先修改group主表
        boolean groupSave = sysGroup.updateById();
        // 修改groupExpand拓展表
        boolean groupExpandSave = sysGroupExpand.update(new QueryWrapper<SysGroupExpand>().eq("groupId", sysGroup.getGroupId()));

        return groupSave && groupExpandSave;
    }

}
