package com.cloud.gateway.feign;

import com.cloud.model.user.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * Date: 31/1/2019
 * Time: 9:59 AM
 * @author liangzheng
 */
@FeignClient("${user-center}")
public interface UserClient {
    /**
     * @param phone
     * @return
     */
    @GetMapping(value = "/phone-anon/internal")
    SysUser findByPhone(@RequestParam("phone") String phone);
}
