package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.exception.ResultException;
import com.cloud.model.user.SysGrouping;
import com.cloud.model.user.SysUser;
import com.cloud.model.user.SysUserGrouping;
import com.cloud.user.dao.SysUserGroupingDao;
import com.cloud.user.service.SysUserGroupingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    @Transactional
    public boolean saveUserCheck(List<Integer> groupingIds, Integer userId) {
        // 非空验证
        if (null == groupingIds || groupingIds.size() == 0) {
            log.error("添加用户可以查看的分组,获取到的分组id为空值");
            throw new ResultException(ResultEnum.GROUPINGID_NULL.getCode(),
                    ResultEnum.GROUPINGID_NULL.getMessage());
        }
        if (null == userId) {
            log.error("添加用户可以查看的分组,获取到的用户id为空值");
            throw new ResultException(ResultEnum.USERID_ISNULL.getCode(),
                    ResultEnum.USERID_ISNULL.getMessage());
        }
        SysUserGrouping sysUserGrouping = SysUserGrouping.builder().userId(userId).build();
        // 先进行删除
        sysUserGrouping.delete(new QueryWrapper<SysUserGrouping>().lambda().eq(SysUserGrouping::getUserId, userId));
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
        // 构建对象
        SysUserGrouping sysUserGrouping = SysUserGrouping.builder().build();
        // 根据userId查找所有的usergrouping对象
        List<SysUserGrouping> sysUserGroupings = sysUserGrouping.selectList(new QueryWrapper<SysUserGrouping>().lambda()
                .eq(SysUserGrouping::getUserId, userId));
        List<SysGrouping> list = new ArrayList<>();
        // 根据groupingId查找出所有的grouping
        for (SysUserGrouping userGrouping : sysUserGroupings) {
            SysGrouping sysGrouping = SysGrouping.builder().groupingId(userGrouping.getGroupingId()).build();
            list.add(sysGrouping);
        }
        return list;
    }
}
