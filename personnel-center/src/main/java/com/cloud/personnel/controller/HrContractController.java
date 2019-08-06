package com.cloud.personnel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.personnel.HrContract;
import com.cloud.model.personnel.HrPersonnel;
import com.cloud.model.user.LoginAppUser;
import com.cloud.personnel.util.PersonnelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 合同信息表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/contract")
public class HrContractController {
    /**
     * @return 查询结果
     * 根据员工编号查询其合同信息
     */
    @GetMapping("/getContract")
    public ResultVo findContractByPersonnelID() {
        // 获取当前的登录对象个人信息
        HrPersonnel hrPersonnel = PersonnelUtil.getPersonnel();

        // 查询员工合同信息
        HrContract contract = HrContract.builder().build();
        List<HrContract> hrContracts = contract.selectList(new QueryWrapper<HrContract>().lambda()
                .eq(HrContract::getPPersonnelid, hrPersonnel.getId()).orderByDesc(HrContract::getId));
        if (hrContracts.size() != 0 && hrContracts != null) {
            return new ResultVo(200, "操作成功", hrContracts.get(0));
        } else {
            return new ResultVo(16610, "没有查到相关信息", null);
        }
    }
}

