package com.gwm.one.hr.personnel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.hr.personnel.util.PersonnelUtil;
import com.gwm.one.model.hr.personnel.HrPersonnel;
import com.gwm.one.model.hr.personnel.HrPhycial;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Slf4j
public class HrPhycialController {
    /**
     * @return 查询结果
     * 根据员工编号查询其体检信息
     */
    @PreAuthorize("hasAuthority('phycialList:query')")
    @GetMapping("/getPhycialList")
    public ResultVo findPhycialListByPersonnelID() {

        try {
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
        } catch (Exception e) {
            log.error("" + e);
            return new ResultVo(16670, e.getMessage(), null);
        }

    }
}

