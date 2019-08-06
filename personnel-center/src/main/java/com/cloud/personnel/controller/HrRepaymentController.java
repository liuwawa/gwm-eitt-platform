package com.cloud.personnel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.personnel.HrPersonnel;
import com.cloud.model.personnel.HrRepayment;
import com.cloud.personnel.util.PersonnelUtil;
import org.springframework.web.bind.annotation.PostMapping;
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
public class HrRepaymentController {

    @PostMapping("/findAll")
    public ResultVo  findRepayment() {
        HrPersonnel personnel = PersonnelUtil.getPersonnel();
        HrRepayment repayment = HrRepayment.builder().build();
        List<HrRepayment> repaymentList = repayment.selectList(new QueryWrapper<HrRepayment>()
                .lambda().eq(HrRepayment::getPPersonnelid, personnel.getId()));
        if (repaymentList.size() == 0 && repaymentList == null) {
            return new ResultVo(16610, "没有查到相关信息", null);
        } else {
            return new ResultVo(200, "操作成功", repayment);
        }
    }


}

