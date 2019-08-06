package com.cloud.personnel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.personnel.HrPersonnel;
import com.cloud.model.personnel.HrPhycial;
import com.cloud.personnel.util.PersonnelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 体检信息表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/phycial")
public class HrPhycialController {
    /**
     * @return 查询结果
     * 根据员工编号查询其体检信息
     */
    @GetMapping("/getPhycialList")
    public ResultVo findPhycialListByPersonnelID() {
        // 获取当前的登录对象个人信息
        HrPersonnel hrPersonnel = PersonnelUtil.getPersonnel();

        // 根据员工编号在体检信息表中查询
        HrPhycial hrPhycial = HrPhycial.builder().build();
        List<HrPhycial> hrPhycials = hrPhycial.selectList(new QueryWrapper<HrPhycial>().lambda()
                .eq(HrPhycial::getPPersonnelid, hrPersonnel.getId()).orderByDesc(HrPhycial::getPlDate));
        if (null != hrPhycials && hrPhycials.size() != 0) {
            return new ResultVo(200, "操作成功", hrPhycials.get(0));
        } else {
            return new ResultVo(16610, "没有查到相关信息", null);
        }

    }
}

