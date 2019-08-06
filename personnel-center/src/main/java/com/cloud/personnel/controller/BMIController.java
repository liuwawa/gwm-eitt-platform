package com.cloud.personnel.controller;

import com.cloud.common.utils.StringUtils;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.personnel.HrPersonnel;
import com.cloud.personnel.util.PersonnelUtil;
import io.swagger.annotations.Api;
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
public class BMIController {

    /**
    * 计算用户BMI
    * */
    @GetMapping("/getPersonBMI")
    public ResultVo getPersonBMI() {
        //获取用户信息
        HrPersonnel personnel = PersonnelUtil.getPersonnel();
        if (personnel == null) {
            return  new ResultVo(16610, "没有查到相关信息", null);
        }

        if (!StringUtils.isEmpty(personnel.getPWeight()) && !StringUtils.isEmpty(personnel.getPHeight())) {
            BigDecimal bmi = getBMI(personnel.getPWeight(), personnel.getPHeight());
            return  new ResultVo(200, "操作成功", bmi);
        } else {
            return  new ResultVo(16610, "没有查到相关信息", null);
        }
    }
    /**
    * 计算输入BMI
    * */
    @GetMapping("/getInputBMI")
    public ResultVo getInputBMI(String weight, String height) {
        //获取用户信息
        if (!StringUtils.isEmpty(weight) && !StringUtils.isEmpty(height)) {
            BigDecimal bmi = getBMI(weight, height);
            return  new ResultVo(200, "操作成功", bmi);
        } else {
            return  new ResultVo(16610, "没有查到相关信息", null);
        }
    }


    /*
    * 计算BMI
    * */
    public BigDecimal getBMI(String weight, String height)  {

        BigDecimal pWeight = new BigDecimal(weight);
        BigDecimal pHeight = new BigDecimal(height).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        //bmi计算公式 体质指数（BMI）=体重（kg）÷身高（m）^2
        BigDecimal bmi = pWeight.divide(pHeight.multiply(pHeight), 2, RoundingMode.HALF_UP).multiply(new BigDecimal("1000"));
        return bmi;
    }

}
