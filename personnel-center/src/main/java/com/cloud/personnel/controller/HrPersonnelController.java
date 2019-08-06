package com.cloud.personnel.controller;


import com.cloud.common.vo.ResultVo;
import com.cloud.model.personnel.HrPersonnel;
import com.cloud.personnel.util.PersonnelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResultVo(200, "操作成功", hrPersonnel);
    }
}

