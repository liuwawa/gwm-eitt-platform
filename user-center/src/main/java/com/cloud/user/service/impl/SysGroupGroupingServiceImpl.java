package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.exception.ResultException;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGroupGrouping;
import com.cloud.model.user.SysGrouping;
import com.cloud.user.dao.SysGroupGroupingDao;
import com.cloud.user.service.SysGroupGroupingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 分组组织中间表 服务实现类
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */
@Service
@Slf4j
public class SysGroupGroupingServiceImpl extends ServiceImpl<SysGroupGroupingDao, SysGroupGrouping> implements SysGroupGroupingService {

    @Override
    @Transactional
    public boolean saveGroupToGrouping(List<Integer> groupIds, Integer groupingId, String groupingName, String groupingRemark, String loginAdminName) {
        // 非空验证
        if (null == groupingId) {
            log.error("添加组织到分组,获取到的分组id为空值");
            throw new ResultException(ResultEnum.GROUPINGID_NULL.getCode(),
                    ResultEnum.GROUPINGID_NULL.getMessage());
        }
        SysGrouping sysGrouping = SysGrouping.builder().groupingId(groupingId).build();
        if (null == sysGrouping.selectById()) {
            log.error("添加组织到分组,添加到的分组不存在，分组id:{}", groupingId);
            throw new ResultException(ResultEnum.GROUPING_NOT_EXIST.getCode(),
                    ResultEnum.GROUPING_NOT_EXIST.getMessage());
        }

        // 修改分组
        SysGrouping grouping = SysGrouping.builder()
                .groupingId(groupingId).groupingName(groupingName).groupingRemark(groupingRemark).updateBy(loginAdminName).updateTime(new Date()).build();
        grouping.updateById();

        // 构建对象
        SysGroupGrouping sysGroupGrouping = SysGroupGrouping.builder().groupingId(groupingId).build();
        // 先删除存在表中的
        sysGroupGrouping.delete(new QueryWrapper<SysGroupGrouping>().lambda().eq(SysGroupGrouping::getGroupingId, groupingId));
        // 如果传来一个空数组直接删除所有的关联关系,直接返回true
        if (groupIds.size() == 0 || groupIds == null) {
            return true;
        }
        // 进行添加
        for (Integer groupId : groupIds) {
            sysGroupGrouping.setGroupId(groupId);
            if (!sysGroupGrouping.insert()) {
                return false;
            }
        }
        return true;
    }


}
