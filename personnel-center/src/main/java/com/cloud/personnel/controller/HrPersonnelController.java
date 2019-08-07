package com.cloud.personnel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.personnel.HrContract;
import com.cloud.model.personnel.HrPersonnel;
import com.cloud.personnel.util.PersonnelUtil;
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
public class HrPersonnelController {
    /**
     * @return 查询结果
     * 根据员工编号查询其员工履历
     */
    @GetMapping("/getPersonnelInfo")
    public ResultVo findContractByPersonnelID() {
        // 获取当前的登录对象个人信息
        HrPersonnel hrPersonnel = PersonnelUtil.getPersonnel();
        // 查询员工合同信息
        HrContract contract = HrContract.builder().build();
        List<HrContract> hrContracts = contract.selectList(new QueryWrapper<HrContract>().lambda()
                .eq(HrContract::getPPersonnelid, hrPersonnel.getId()).eq(HrContract::getCStatus, "有效").orderByDesc(HrContract::getId));
        // 有合同信息
        if (hrContracts.size() != 0 && hrContracts != null) {
            // endDate是9999-12-30表示无固定期限
            if (hrContracts.get(0).getCEnddate().toString().equals("9999-12-30")) {
                hrPersonnel.setContractDate(hrContracts.get(0).getCStartdate() + "---无固定期限");
            }
            // 日期做字符串连接
            hrPersonnel.setContractDate(hrContracts.get(0).getCStartdate().toString() + hrContracts.get(0).getCEnddate().toString());
            return new ResultVo(200, "操作成功", hrPersonnel);
        } else {
            return new ResultVo(16610, "没有查到相关信息", null);
        }
    }
}

