package com.cloud.personnel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.personnel.HrContract;
import com.cloud.model.user.LoginAppUser;
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
     *
     * @return 查询结果
     * 根据员工编号查询其合同信息
     */
    @GetMapping("/getContract")
    public ResultVo findContractByPersonnelID() {
        // 获取当前的登录对象
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String personnelID = loginAppUser.getPersonnelID();

        // 根据对象的员工编号在个人信息表中查找是否为在职

        // 在职人员显示有效的劳动合同
        HrContract contract = HrContract.builder().build();
        List<HrContract> hrContracts = contract.selectList(new QueryWrapper<HrContract>().lambda()
                .eq(HrContract::getPPersonnelid, personnelID));

        // 离职人员显示最近的劳动合同
        return null;
    }
}

