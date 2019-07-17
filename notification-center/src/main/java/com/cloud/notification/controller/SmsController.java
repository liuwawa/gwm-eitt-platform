package com.cloud.notification.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.exception.SmsException;
import com.cloud.common.utils.PhoneUtil;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.notification.model.Sms;
import com.cloud.notification.model.VerificationCode;
import com.cloud.notification.service.SmsService;
import com.cloud.notification.service.VerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@Api(value = "短信", tags = {"短信操作接口 SmsController"})
public class SmsController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private SmsService smsService;

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @PostMapping(value = "/notification-anon/codes", params = {"phone"})
    @ApiOperation(value = "发送短信验证码")
    public VerificationCode sendSmsVerificationCode(@ApiParam(value = "phone",required = true) String phone) {
        if (!PhoneUtil.checkPhone(phone)) {
            throw new IllegalArgumentException("手机号格式不正确");
        }

        return verificationCodeService.generateCode(phone);
    }

    @PostMapping(value = "/notification-anon/sms/codes", params = {"phone"})
    @ApiOperation(value = "发送短信验证码")
    public VerificationCode sendSmsLoginVerificationCode(@ApiParam(value = "phone",required = true)String phone) {
        if (!PhoneUtil.checkPhone(phone)) {
            throw new SmsException("手机号格式不正确");
        }

        return verificationCodeService.generateCode(phone);
    }

    /**
     * 根据验证码和key获取手机号
     *
     * @param key
     * @param code
     * @param delete 是否删除key
     * @param second 不删除时，可重置过期时间（秒）
     * @return
     */
    @GetMapping(value = "/notification-anon/internal/phone", params = {"key", "code"})
    @ApiOperation(value = "根据验证码和key获取手机号")
    public String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second) {
        return verificationCodeService.matcheCodeAndGetPhone(key, code, delete, second);
    }


    /**
     * 查询短信发送记录
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('sms:query')")
    @GetMapping("/sms")
    @ApiOperation(value = "查询短信发送记录")
    public Page<Sms> findSms(@RequestParam Map<String, Object> params) {
        return smsService.findSms(params);
    }

    @PreAuthorize("hasAuthority('sms:query')")
    @PostMapping("/findSmsPage")
    @ApiOperation(value = "分页多条件查询短信记录",notes = "参数，pageNum，pageSize，condition（手机号）")
    public PageResult findPage(@RequestBody Map<String, Object> params) {
        Long pageIndex = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String condition = String.valueOf(params.get("condition").toString());

        QueryWrapper<Sms> queryWrapper = new QueryWrapper<>();

        if(!"".equals(condition)){
            queryWrapper.eq("phone",condition);
        }
        IPage<Sms> smsIPage = smsService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),queryWrapper);
        return PageResult.builder().content(smsIPage.getRecords()).
                pageNum(smsIPage.getCurrent()).
                pageSize(smsIPage.getSize()).
                totalPages(smsIPage.getPages()).
                totalSize(smsIPage.getTotal()).build();
    }


    @LogAnnotation(module = LogModule.DELETE_ROLE)
    @PreAuthorize("hasAuthority('back:sms:delete')")
    @DeleteMapping("/delSms/{id}")
    @ApiOperation(value = "根据id删除短信信息记录")
    public ResultVo deleteSms(@PathVariable Long id) {
        try {
            smsService.removeById(id);
            return ResultVo.builder().msg("删除成功").data(null).code(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.builder().msg("删除失败").data(null).code(200).build();
        }
    }

    @LogAnnotation(module = LogModule.DELETE_ROLE)
    @PreAuthorize("hasAuthority('back:sms:delete')")
    @DeleteMapping("/delAll")
    @ApiOperation(value = "删除全部短信记录")
    public ResultVo deleteAll() {
        try {
            smsService.delAllSms();
            return ResultVo.builder().msg("删除成功").data(null).code(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.builder().msg("删除失败").data(null).code(500).build();
        }
    }


}
