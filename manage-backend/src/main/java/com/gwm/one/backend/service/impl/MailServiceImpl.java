package com.gwm.one.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwm.one.backend.service.MailService;
import com.gwm.one.backend.service.SendMailService;
import com.gwm.one.backend.dao.MailDao;
import com.gwm.one.common.utils.AppUserUtil;
import com.gwm.one.common.utils.PageUtil;
import com.gwm.one.model.common.Page;
import com.gwm.one.model.mail.Mail;
import com.gwm.one.model.mail.constants.MailStatus;
import com.gwm.one.model.hr.user.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MailServiceImpl extends ServiceImpl<MailDao,Mail> implements MailService {

    @Autowired
    private MailDao mailDao;
    @Autowired
    private SendMailService sendMailService;

    /**
     * 保存邮件
     *
     * @param mail
     */
    @Transactional
    @Override
    public void saveMail(Mail mail) {
        if (mail.getUserId() == null || StringUtils.isBlank(mail.getUsername())) {
            SysUser appUser = AppUserUtil.getLoginAppUser();
            if (appUser != null) {
                mail.setUserId(appUser.getId());
                mail.setUsername(appUser.getUsername());
            }
        }
        if (mail.getUserId() == null) {
            mail.setUserId(0L);
            mail.setUsername("系统邮件");
        }

        if (mail.getCreateTime() == null) {
            mail.setCreateTime(new Date());
        }
        mail.setUpdateTime(mail.getCreateTime());
        mail.setStatus(MailStatus.DRAFT);

        mailDao.save(mail);
        log.info("保存邮件：{}", mail);
    }

    /**
     * 修改未发送邮件
     *
     * @param mail
     */
    @Transactional
    @Override
    public void updateMail(Mail mail) {
        Mail oldMail = mailDao.findById(mail.getId());
        if (oldMail.getStatus() == MailStatus.SUCCESS) {
            throw new IllegalArgumentException("已发送的邮件不能编辑");
        }
        mail.setUpdateTime(new Date());

        mailDao.updateEmail(mail);

        log.info("修改邮件：{}", mail);
    }

    /**
     * 异步发送邮件
     *
     * @param mail
     */
    @Override
    @Async
    public void sendMail(Mail mail) {
        boolean flag = sendMailService.sendMail(mail.getToEmail(), mail.getSubject(), mail.getContent());
        mail.setSendTime(new Date());
        mail.setStatus(flag ? MailStatus.SUCCESS : MailStatus.ERROR);

        mailDao.updateEmail(mail);
    }

    @Override
    public Mail findById(Long id) {
        return mailDao.findById(id);
    }

    @Override
    public Page<Mail> findMails(Map<String, Object> params) {
        int total = mailDao.count(params);
        List<Mail> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);

            list = mailDao.findData(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public List<Mail> findNoRead(Long userId) {
        return mailDao.findNoRead(userId);
    }

    @Override
    public void updateIsRead(Long id) {
        mailDao.updateIsRead(id);
    }

//    @Override
//    public void updateIsReadByUserId(Long userId) {
//        mailDao.updateIsReadByUserId(userId);
//    }


}
