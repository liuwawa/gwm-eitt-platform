package com.cloud.oauth.handler;

import com.cloud.common.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import static com.cloud.common.constants.Messages.msgMap;

/**
 * Created on 2018/5/24 0024.
 *
 * @author zlf
 * @email i@merryyou.cn
 * @since 1.0
 */
@Component("customWebResponseExceptionTranslator")
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<RuntimeException> translate(Exception e) throws Exception {
        String[] split = StringUtils.split(e.getMessage(), ":");
        String msg = msgMap.get(split[0]);
        if (msg == null) {
            msg = e.getMessage();
        }
        if (split.length > 1) {
            msg = msg + " : " + split[1];
        }
        if (e instanceof AuthenticationServiceException) {
            return HttpUtils.getResponseEntity(msg);
        } else if (e instanceof OAuth2Exception) {
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            return ResponseEntity
                    .status(oAuth2Exception.getHttpErrorCode())
                    .body(new CustomOauthException(msg));
        }
        return null;
    }

}
