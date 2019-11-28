package com.gwm.one.hr.personnel.controller;

import com.gwm.one.common.utils.StringUtils;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.hr.personnel.util.PersonnelUtil;
import com.gwm.one.model.hr.personnel.HrPersonnel;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * 我的BMI
 * 王懋云
 * */
@RestController
@Api
@Slf4j
public class BMIController {

    /**
     * 计算用户BMI
     */
    @PreAuthorize("hasAuthority('BMI:query')")
    @GetMapping("/getPersonBMI")
    public ResultVo getPersonBMI() {

        try {
            //获取用户信息
            HrPersonnel personnel = PersonnelUtil.getPersonnel();
            if (personnel == null) {
                return new ResultVo(16610, "没有查到相关信息", null);
            }

            if (!StringUtils.isEmpty(personnel.getPWeight()) && !StringUtils.isEmpty(personnel.getPHeight())) {
                BigDecimal bmi = getBMI(personnel.getPWeight(), personnel.getPHeight());
                return new ResultVo(200, "操作成功", bmi);
            } else {
                return new ResultVo(16610, "没有查到相关信息", null);
            }
        } catch (Exception e) {
            log.error("" + e);
            return new ResultVo(16680, e.getMessage(), null);
        }
    }

    /**
     * 计算输入BMI
     */
    @PreAuthorize("hasAuthority('BMI:query')")
    @GetMapping("/getInputBMI")
    public ResultVo getInputBMI(String weight, String height) {
        //获取用户信息
        if (!StringUtils.isEmpty(weight) && !StringUtils.isEmpty(height)) {
            BigDecimal bmi = getBMI(weight, height);
            return new ResultVo(200, "操作成功", bmi);
        } else {
            return new ResultVo(16610, "没有查到相关信息", null);
        }
    }


    /*
     * 计算BMI
     * */
    public BigDecimal getBMI(String weight, String height) {

        BigDecimal pWeight = new BigDecimal(weight);
        BigDecimal pHeight = new BigDecimal(height).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        //bmi计算公式 体质指数（BMI）=体重（kg）÷身高（m）^2
        BigDecimal bmi = pWeight.divide(pHeight.multiply(pHeight), 2, RoundingMode.HALF_UP);
        return bmi;
    }

}
