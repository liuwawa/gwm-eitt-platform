package com.gwm.one.hr.personnel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.hr.personnel.util.PersonnelUtil;
import com.gwm.one.model.hr.personnel.PxInnerTeacher;
import com.gwm.one.model.hr.personnel.HrPersonnel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-08-07
 */
@RestController
@RequestMapping("/innerTeacher")
@Slf4j
public class PxInnerTeacherController {
    /**
     * @return 查询结果
     * 根据员工编号查询其申请讲师记录
     */
    @PreAuthorize("hasAuthority('applyTeacherInfo:query')")
    @GetMapping("/getApplyTeacherInfo")
    public ResultVo findApplyTeacherInfoByPersonnelID() {
        try {
            // 获取当前的登录对象个人信息
            HrPersonnel hrPersonnel = PersonnelUtil.getPersonnel();

            // 查询员工的申请记录
            PxInnerTeacher pxInnerTeacher = PxInnerTeacher.builder().build();
            List<PxInnerTeacher> pxInnerTeachers = pxInnerTeacher.selectList(new QueryWrapper<PxInnerTeacher>().lambda()
                    .eq(PxInnerTeacher::getPPersonnelid, hrPersonnel.getId()).orderByDesc(PxInnerTeacher::getCreateTime));
            if (pxInnerTeachers.size() != 0 && pxInnerTeachers != null) {
                return new ResultVo(200, "操作成功", pxInnerTeachers);
            } else {
                return new ResultVo(16610, "没有查到相关信息", null);
            }
        } catch (Exception e) {
            log.error("" + e);
            return new ResultVo(16680, e.getMessage(), null);
        }
    }
}

