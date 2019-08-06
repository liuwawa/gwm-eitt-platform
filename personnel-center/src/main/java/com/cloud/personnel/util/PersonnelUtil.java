package com.cloud.personnel.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.personnel.HrPersonnel;
import com.cloud.model.user.LoginAppUser;

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
        return hrPersonnel;
    }
}
