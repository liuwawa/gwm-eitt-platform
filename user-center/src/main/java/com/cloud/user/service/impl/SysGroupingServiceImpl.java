package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.exception.ResultException;
import com.cloud.model.user.SysGroupGrouping;
import com.cloud.model.user.SysGrouping;
import com.cloud.model.user.SysUserGrouping;
import com.cloud.user.dao.SysGroupingDao;
import com.cloud.user.service.SysGroupingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
        if (userGroupings != null && userGroupings.size() != 0) {
            throw new ResultException(500, "用户使用分组中，请先解除关系");
        }
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
        // 先添加生成一个分组
        grouping.insert();
        // 构建对象
        SysGroupGrouping sysGroupGrouping = SysGroupGrouping.builder().groupingId(grouping.getGroupingId()).build();
        // 添加到中间表
        for (Integer groupId : list) {
            sysGroupGrouping.setGroupId(groupId);
            if (!sysGroupGrouping.insert()) {
                return false;
            }
        }
        return true;
    }
}
