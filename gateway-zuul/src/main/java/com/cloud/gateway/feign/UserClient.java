package com.cloud.gateway.feign;

import com.cloud.model.user.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * Date: 31/1/2019
 * Time: 9:59 AM
 *
 * @author liangzheng
 */
@FeignClient(value = "${user-center}", fallback = UserClient.UserClientFeign.class)
public interface UserClient {
    /**
     * @param phone
     * @return
     */
    @GetMapping(value = "/phone-anon/internal")
    SysUser findByPhone(@RequestParam("phone") String phone);


    @Component("userClientFeign")
    final class UserClientFeign implements UserClient {

        @Override
        public SysUser findByPhone(String phone) {
            return null;
        }
    }
}
