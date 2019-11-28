package com.gwm.one.hr.personnel.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gwm.one.common.exception.ResultException;
import com.gwm.one.common.utils.AppUserUtil;
import com.gwm.one.model.hr.personnel.HrPersonnel;
import com.gwm.one.model.hr.user.LoginAppUser;

public class PersonnelUtil {
    // 获取当前的用户信息
    public static HrPersonnel getPersonnel() {
        // 获取当前的登录对象
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String personnelNO = loginAppUser.getPersonnelNO();

        // 根据对象的员工编号在个人信息表中查找
        HrPersonnel personnel = HrPersonnel.builder().build();
        HrPersonnel hrPersonnel = personnel.selectOne(new QueryWrapper<HrPersonnel>()
                .lambda().eq(HrPersonnel::getPPersonnelno, personnelNO)
                .eq(HrPersonnel::getPStatus, "1"));
        if (null == hrPersonnel) {
            throw new ResultException(17760, "没有找到该员工的相关信息！");
        }
        return hrPersonnel;
    }
}
