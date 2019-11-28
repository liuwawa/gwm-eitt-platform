package com.gwm.one.hr.personnel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.hr.personnel.util.PersonnelUtil;
import com.gwm.one.model.hr.personnel.HrContract;
import com.gwm.one.model.hr.personnel.HrPersonnel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-07-26
 */
@RestController
@RequestMapping("/personnel")
@Slf4j
public class HrPersonnelController {
    /**
     * @return 查询结果
     * 根据员工编号查询其员工履历
     */
    @PreAuthorize("hasAuthority('personnelInfo:query')")
    @GetMapping("/getPersonnelInfo")
    public ResultVo findPersonnelInfoByPersonnelID() {
        try {
            // 获取当前的登录对象个人信息
            HrPersonnel hrPersonnel = PersonnelUtil.getPersonnel();
            // 查询员工合同信息
            HrContract contract = HrContract.builder().build();
            List<HrContract> hrContracts = contract.selectList(new QueryWrapper<HrContract>().lambda()
                    .eq(HrContract::getPPersonnelid, hrPersonnel.getId()).eq(HrContract::getCStatus, "有效").orderByDesc(HrContract::getId));
            // 有合同信息
            if (hrContracts.size() == 0 || hrContracts == null) {
                hrPersonnel.setContractDate("暂无");
            } else {
                // endDate是9999-12-30表示无固定期限
                String endDate = hrContracts.get(0).getCEnddate().toString();
                String subEndDate = endDate.substring(0, 10);
                log.info("合同截止日期" + subEndDate);
                if (subEndDate.equals("9999-12-30")) {
                    hrPersonnel.setContractDate(hrContracts.get(0).getCStartdate() + " --- 无固定期限");
                } else {
                    // 日期做字符串连接
                    hrPersonnel.setContractDate(
                            hrContracts.get(0).getCStartdate().toString() + " --- " + endDate);
                }

            }
            return new ResultVo(200, "操作成功", hrPersonnel);
        } catch (Exception e) {
            log.error("" + e);
            return new ResultVo(500, e.getMessage(), null);
        }


    }
}

