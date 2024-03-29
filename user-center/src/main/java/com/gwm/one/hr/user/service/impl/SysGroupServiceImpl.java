package com.gwm.one.hr.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwm.one.common.enums.ResultEnum;
import com.gwm.one.common.exception.ResultException;
import com.gwm.one.hr.user.dao.SysGroupDao;
import com.gwm.one.hr.user.dao.SysUserDao;
import com.gwm.one.hr.user.service.SysGroupService;
import com.gwm.one.model.hr.user.SysGroup;
import com.gwm.one.model.hr.user.SysGroupExpand;
import com.gwm.one.model.hr.user.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        SysGroup selectOne = sysGroup.selectOne(new QueryWrapper<SysGroup>().lambda().eq(SysGroup::getLabel, sysGroup.getLabel()));
        if (null != selectOne) {
            throw new ResultException(500, "组织名称重复！");
        }
        // 先添加group主表
        boolean groupSave = sysGroup.insert();
        if (sysGroup.getParentid() != 0) {
            checkGroupLevel(sysGroup);
        }


        // 给expand拓展表添加外键groupId
        sysGroupExpand.setGroupId(sysGroup.getId());

        // 设置gGrade
        sysGroupExpand = setGgradeByLevel(sysGroup, sysGroupExpand);

        // 判断新增的组织是哪个级别
        if (!sysGroupExpand.getGGrade().equals("0")) {   //不是集团级别，肯定有父级，是集团级别也不会有这六个属性
            boolean flag = true;
            // 根据其父id查找出其上一级的组织拓展数据
            while (flag) {

                // 根据传来的父id查找出父对象
                SysGroup group = sysGroup.selectOne(new QueryWrapper<SysGroup>().lambda()
                        .eq(SysGroup::getIsDel, "0")
                        .eq(SysGroup::getId, sysGroup.getParentid()));
                if (group == null) {
                    flag = false;
                    break;
                }
                // 根据父对象的id查出他的拓展数据
                SysGroupExpand groupExpand = sysGroupExpand.selectOne(new QueryWrapper<SysGroupExpand>().lambda()
                        .eq(SysGroupExpand::getGroupId, group.getId()));
                if (null == groupExpand) {
                    throw new ResultException(500, "添加的父级没有拓展信息，无法添加！");
                }
                // 为拓展数据赋值
                sysGroupExpand = setProperty(groupExpand, sysGroupExpand, group);
                // 为sysGroup重新赋值父id， 循环查找
                sysGroup.setParentid(group.getParentid());

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
            default:
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
        if (groupExpand.getGFullname() != null) {
            groupExpand.setGFullname(sysGroup.getLabel() + groupExpand.getGFullname());
        } else {
            groupExpand.setGFullname(sysGroup.getLabel());
        }
        return groupExpand;

    }

    public void checkGroupLevel(SysGroup sysGroup) {
        SysGroup group1 = sysGroup.selectOne(new QueryWrapper<SysGroup>().
                lambda().eq(SysGroup::getId, sysGroup.getParentid()).eq(SysGroup::getIsDel, 0));
        if (group1.getLevel() >= sysGroup.getLevel()) {
            throw new ResultException(16670, "请输入合理的组织级别！");
        }
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
        // 从表中查出
        SysGroup group1 = sysGroup.selectById();
        if (group1.getParentid() != 0) {
            checkGroupLevel(group1);
        }

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
                if (group == null) {
                    flag = false;
                    break;
                }
                // 根据父对象的id查出他的拓展数据
                SysGroupExpand groupExpand = sysGroupExpand.selectOne(new QueryWrapper<SysGroupExpand>().lambda()
                        .eq(SysGroupExpand::getGroupId, group.getId()));
                // 为拓展数据赋值
                sysGroupExpand = setProperty(groupExpand, sysGroupExpand, group);
                // 为sysGroup重新赋值父id，循环查找
                sysGroup.setParentid(group.getParentid());
            }
        }
        SysGroupExpand groupExpand = sysGroupExpand
                .selectOne(new QueryWrapper<SysGroupExpand>().lambda().eq(SysGroupExpand::getGroupId, sysGroup.getId()));
        boolean f = false;
        if (groupExpand != null) {
            // 再修改groupExpand拓展表
            f = sysGroupExpand.update(new QueryWrapper<SysGroupExpand>().lambda()
                    .eq(SysGroupExpand::getGroupId, sysGroup.getId()));
        } else {
            // 设置groupId，添加一个拓展表
            sysGroupExpand.setGroupId(sysGroup.getId());
            f = sysGroupExpand.insert();
        }


        return groupSave && f;
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
        List<SysUser> userList = sysUserDao.selectList(new QueryWrapper<SysUser>().lambda().eq(SysUser::getEnabled, 1));
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
    public boolean changeGroup(List<Integer> groupIds, Integer parentId, String loginAdminName) {
        if (null == parentId) { // 非空验证
            log.error("移动组织结构,获取到的组织id为空值");
            throw new ResultException(ResultEnum.GROUPID_NULL.getCode(),
                    ResultEnum.GROUPID_NULL.getMessage());
        }
        SysGroup sysGroup = SysGroup.builder().build();  // 创建查询对象
        List<SysGroup> groupList = new ArrayList<>();
        groupIds.forEach(i -> {
            SysGroup group = sysGroup.selectById(i); // 查出需要的组织，并设置
            groupList.add(group);
        });
        SysGroup group = groupList.get(0); // 只设置第一个子节点的父节点到想要移动到的节点上，其他的不影响
        group.setParentid(parentId);
        SysGroup parentGroup = sysGroup.selectById(parentId); // 根据想要设置到的节点id找到该组织
        Integer parentLevel = parentGroup.getLevel(); // 找到想要设置到的级层的上级层
        Integer level = group.getLevel(); // 找到没移动之前最大节点的级层
        Integer wiLevel = setGroupLevel(parentLevel); // 算出将要设置的级层
        if (wiLevel > level) {   // 重新设置所有的level
            Integer subLevel = wiLevel - level;
            for (SysGroup sysGroup1 : groupList) {
                sysGroup1.setLevel(sysGroup1.getLevel() + subLevel);
            }
        } else if (wiLevel < level) {
            Integer subLevel = level - wiLevel;
            for (SysGroup sysGroup1 : groupList) {
                sysGroup1.setLevel(sysGroup1.getLevel() - subLevel);
            }
        }
        for (SysGroup wiGroup : groupList) {
            if (wiGroup.getLevel() > 6) {
                wiGroup.setLevel(6); // level大于6的都设置为6(最大到6)
            }
            wiGroup.setUpdateBy(loginAdminName);
            wiGroup.setUpdateTime(new Date());
            wiGroup.setIsUpdate("1");
            if (!wiGroup.updateById()) { // 做主表的更新操作
                throw new ResultException(500, "更改出现错误！");
            }
        }
        SysGroupExpand groupExpand = SysGroupExpand.builder().build();
        List<SysGroupExpand> groupExpands = groupExpand.selectList(new QueryWrapper<SysGroupExpand>().lambda()
                .in(SysGroupExpand::getGroupId, groupIds)); // 找出所有的拓展对象
        List<SysGroupExpand> sysGroupExpands = new ArrayList<>();

        for (int i = 0; i < groupList.size(); i++) { // 重新设置其下的所有单位，部门和科室
            SysGroupExpand sysGroupExpand = setGgradeByLevel(groupList.get(i), groupExpands.get(i));  // 设置gGrade
            sysGroupExpands.add(sysGroupExpand);
        }

        for (SysGroupExpand sysGroupExpand : sysGroupExpands) {
            // 判断修改的组织是哪个级别
            if (!sysGroupExpand.getGGrade().equals("0")) {   //不是集团级别，肯定有父级，是集团级别也不会有这六个属性
                boolean flag = false;
                // 根据其父id查找出其上一级的组织拓展数据
                while (flag) {
                    // 根据传来的父id查找出父对象
                    SysGroup group1 = sysGroup.selectOne(new QueryWrapper<SysGroup>().lambda()
                            .eq(SysGroup::getIsDel, "0")
                            .eq(SysGroup::getId, parentId));
                    if (group1 == null) {
                        flag = false;
                        break;
                    }
                    // 根据父对象的id查出他的拓展数据
                    SysGroupExpand groupExpand1 = sysGroupExpand.selectOne(new QueryWrapper<SysGroupExpand>().lambda()
                            .eq(SysGroupExpand::getGroupId, group1.getId()));
                    // 为拓展数据赋值
                    sysGroupExpand = setProperty(groupExpand1, sysGroupExpand, group1);
                    // 为sysGroup重新赋值父id，循环查找
                    sysGroup.setParentid(group1.getParentid());
                }
            }
            if (!sysGroupExpand.updateById()) {
                throw new ResultException(500, "更改出现错误！");
            }
        }
        return true;
    }

    // 根据父级level设置子级level
    public static Integer setGroupLevel(Integer parentLevel) {
        Integer level = 0;
        switch (parentLevel) {
            case 0:
                level = 1;
                break;
            case 1:
                level = 2;
                break;
            case 2:
                level = 3;
                break;
            case 3:
                level = 4;
                break;
            case 4:
                level = 5;
                break;
            case 5:
                level = 6;
                break;
            case 6:
                level = 6;
                break;
            default:
                level = 6;
                break;
        }
        return level;
    }


}
