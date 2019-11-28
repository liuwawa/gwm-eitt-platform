package com.gwm.one.hr.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwm.one.hr.user.service.UserCredentialsService;
import com.gwm.one.model.hr.user.UserCredential;
import com.gwm.one.hr.user.dao.UserCredentialsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCredentialServiceImpl extends ServiceImpl<UserCredentialsDao, UserCredential> implements UserCredentialsService {
}
