package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.exception.ResultException;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGroupExpand;
import com.cloud.user.dao.SysGroupExpandDao;
import com.cloud.user.service.SysGroupExpandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 组织拓展表 服务实现类
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@Service
@Slf4j
public class SysGroupExpandServiceImpl extends ServiceImpl<SysGroupExpandDao, SysGroupExpand> implements SysGroupExpandService {
    @Override
    @Transactional
    public boolean save(SysGroupExpand sysGroupExpand) {

        // 非空验证
        if (null == sysGroupExpand.getGroupId()) {
            log.error("添加组织拓展表时，主表id为空");
            throw new ResultException(ResultEnum.GROUPID_NULL.getCode(),
                    ResultEnum.GROUPID_NULL.getMessage());
        }
        // 是否在主表存在组织
        SysGroup sysGroup = SysGroup.builder().id(sysGroupExpand.getGroupId()).build();
        if (null == sysGroup.selectById()) {
            log.error("添加组织拓展表时，不存在该组织，groupId为:{}", sysGroupExpand.getGroupId());
            throw new ResultException(ResultEnum.GROUP_NOT_EXIST.getCode(),
                    ResultEnum.GROUP_NOT_EXIST.getMessage());
        }
        return sysGroupExpand.insert();
    }
}
