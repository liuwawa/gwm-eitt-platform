package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.exception.ResultException;
import com.cloud.model.user.SysGrouping;
import com.cloud.user.dao.SysGroupingDao;
import com.cloud.user.service.SysGroupingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
   /* @Override
    @Transactional
    public boolean save(SysGrouping sysGrouping) {
        // 非空验证
        if (null == sysGrouping.getGroupingName()) {
            log.info("添加分组时传入了空的分组名字");
            throw new ResultException(ResultEnum.GROUPINGNAME_NULL.getCode(),
                    ResultEnum.GROUPINGNAME_NULL.getMessage());
        }
        // 设置创建时间
        sysGrouping.setCreateTime(new Date());
        sysGrouping.setCreateBy(sysGrouping.getLoginAdminName());
        return sysGrouping.insert();
    }*/
}
