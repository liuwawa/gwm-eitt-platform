package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.model.user.UserCredential;
import com.cloud.user.dao.UserCredentialsDao;
import com.cloud.user.service.UserCredentialsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCredentialServiceImpl extends ServiceImpl<UserCredentialsDao, UserCredential> implements UserCredentialsService {
}
