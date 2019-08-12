package com.cloud.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.backend.service.MailService;
import com.cloud.common.plugins.ApiJsonObject;
import com.cloud.common.plugins.ApiJsonProperty;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.mail.Mail;
import com.cloud.model.user.LoginAppUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mails")
@Api(value = "邮件", tags = {"邮件操作接口 MailController"})
public class MailController {

    @Autowired
    private MailService mailService;

    @PreAuthorize("hasAuthority('mail:query')")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询邮件")
    public Mail findById(@PathVariable Long id) {
        return mailService.findById(id);
    }

    @PreAuthorize("hasAuthority('mail:query')")
    @GetMapping
    public Page<Mail> findMails(@RequestParam Map<String, Object> params) {
        return mailService.findMails(params);
    }

    /**
     * 保存邮件
     *
     * @param mail
     * @param send 是否发送邮件
     * @return
     */
    @LogAnnotation(module = LogModule.ADD_MAIL)
    @PreAuthorize("hasAuthority('mail:save')")
    @PostMapping
    @ApiOperation(value = "保存邮件")
    public Mail save(@RequestBody Mail mail,@ApiParam(value = "是否发送邮件 boolean",required = true) Boolean send) {
        mailService.saveMail(mail);
        if (Boolean.TRUE.equals(send)) {
            mailService.sendMail(mail);
        }

        return mail;
    }

    /**
     * 修改邮件
     *
     * @param mail
     * @param send 是否发送
     * @return
     */
    @LogAnnotation(module = LogModule.UPDATE_MAIL)
    @PreAuthorize("hasAuthority('mail:update')")
    @ApiOperation(value = "修改邮件")
    @PutMapping
    public Mail update(@RequestBody Mail mail, @ApiParam(value = "是否发送邮件 boolean",required = true) Boolean send) {
        mailService.updateMail(mail);
        if (Boolean.TRUE.equals(send)) {
            mailService.sendMail(mail);
        }

        return mail;
    }

    @PreAuthorize("hasAuthority('mail:query')")
    @PostMapping("/findPage")
    @ApiOperation(value = "分页，多条件查询邮件", notes = "参数：pageNum（必填），pageSize（必填），username,toMail", response = PageResult.class)
    public PageResult findPage(
            @ApiJsonObject(name = "分页，多条件查询邮件", value = {
                    @ApiJsonProperty(key = "pageNum", example = "1", description = "pageNum"),
                    @ApiJsonProperty(key = "pageSize", example = "10", description = "pageSize"),
                    @ApiJsonProperty(key = "username", example = "username", description = "username"),
                    @ApiJsonProperty(key = "toMail", example = "toMail", description = "toMail")
            })
            @RequestBody Map<String, Object> params) {
        Long pageIndex = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String username = String.valueOf(params.get("username").toString());
        String toMail = String.valueOf(params.get("toMail").toString());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        QueryWrapper<Mail> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("username", username).like("toEmail", toMail).eq("userId", loginAppUser.getId());

        IPage<Mail> mailIPage = mailService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize), queryWrapper);
        return PageResult.builder().content(mailIPage.getRecords()).
                pageNum(mailIPage.getCurrent()).
                pageSize(mailIPage.getSize()).
                totalPages(mailIPage.getPages()).
                totalSize(mailIPage.getTotal()).build();
    }


    @LogAnnotation(module = LogModule.DELETE_ROLE)
    @PreAuthorize("hasAuthority('back:mail:delete')")
    @DeleteMapping("/delBatch/{ids}")
    @ApiOperation(value = "批量删除", notes = "以,拼接的id字符串 ")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo deleteBatchMail(@PathVariable String ids) {
        List<Integer> list = new ArrayList<>();
        try {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                list.add(Integer.valueOf(id[i]));
            }

            mailService.removeByIds(list);
            return ResultVo.builder().msg("删除成功").data(null).code(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.builder().msg("删除失败").data(null).code(500).build();
        }
    }


    @PostMapping("/findNoRead")
    @ApiOperation(value ="查找未阅读的邮件")
    public List<Mail> findNoRead() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<Mail> noRead = mailService.findNoRead(loginAppUser.getId());
        return noRead;
    }

    @PostMapping("/batchRead")
    @ApiOperation(value ="批量阅读邮件")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo batchRead() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Mail mail = new Mail();
        mail.setIsRead(1);
        QueryWrapper<Mail> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Mail::getUserId, loginAppUser.getId());
        try {
            mailService.update(mail, queryWrapper);
            return ResultVo.builder().msg("成功").data(null).code(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.builder().msg("失败").data(null).code(500).build();
        }
    }


    @PostMapping("/alreadyRead/{id}")
    @ApiOperation(value ="改变阅读状态")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo alreadyRead(@PathVariable Long id) {
        try {
            mailService.updateIsRead(id);
            return ResultVo.builder().msg("删除成功").data(null).code(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.builder().msg("删除失败").data(null).code(500).build();
        }
    }
}
