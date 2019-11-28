package com.gwm.one.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwm.one.model.common.Page;
import com.gwm.one.model.mail.Mail;

import java.util.List;
import java.util.Map;

public interface MailService extends IService<Mail> {

    void saveMail(Mail mail);

    void updateMail(Mail mail);

    void sendMail(Mail mail);

    Mail findById(Long id);

    Page<Mail> findMails(Map<String, Object> params);

    List<Mail> findNoRead(Long userId);

    void updateIsRead(Long id);

}
