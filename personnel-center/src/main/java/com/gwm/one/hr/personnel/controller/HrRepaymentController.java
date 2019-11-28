package com.gwm.one.hr.personnel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.hr.personnel.util.PersonnelUtil;
import com.gwm.one.model.hr.personnel.HrPersonnel;
import com.gwm.one.model.hr.personnel.HrRepayment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 长城家园还款记录表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/repayment")
@Slf4j
public class HrRepaymentController {

    /**
     * 根据员工编号查询该员工的长城家园还款记录
     *
     * @return 查询结果
     */
    @PreAuthorize("hasAuthority('repayment:query')")
    @GetMapping("/findAllRepayment")
    public ResultVo findRepayment() {
        try {
            HrPersonnel personnel = PersonnelUtil.getPersonnel();
            HrRepayment repayment = HrRepayment.builder().build();
            List<HrRepayment> repaymentList = repayment.selectList(new QueryWrapper<HrRepayment>()
                    .lambda().eq(HrRepayment::getPPersonnelid, personnel.getId()));
            if (repaymentList.size() == 0 && repaymentList == null) {
                return new ResultVo(16610, "没有查到相关信息", null);
            } else {
                return new ResultVo(200, "操作成功", repaymentList);
            }
        } catch (Exception e) {
            log.error("" + e);
            return new ResultVo(16670, e.getMessage(), null);
        }
    }

}

