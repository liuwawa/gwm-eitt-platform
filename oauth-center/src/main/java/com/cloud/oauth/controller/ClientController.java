package com.cloud.oauth.controller;

import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.oauth.SystemClientInfo;
import com.cloud.oauth.service.impl.RedisClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * client管理功能
 * @author liangzheng
 */
@Slf4j
@RestController
@RequestMapping("/clients")
@Api(value = "client管理", tags = "client管理")
public class ClientController {

    @Autowired
    private RedisClientDetailsService clientDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasAuthority('client:save')")
    @LogAnnotation(module = LogModule.ADD_CLIENT)
    @PostMapping
    @ApiOperation(value = "新增保存")
    public void save(@ApiParam(value = "参数：实体clientDetails", required = true) @RequestBody BaseClientDetails clientDetails) {
        ClientDetails client = getAndCheckClient(clientDetails.getClientId(), false);
        if (client != null) {
            throw new IllegalArgumentException(clientDetails.getClientId() + "已存在");
        }
        // 密码加密
        clientDetails.setClientSecret(bCryptPasswordEncoder.encode(clientDetails.getClientSecret()));

        clientDetailsService.addClientDetails(clientDetails);
        log.info("保存client信息：{}", clientDetails);
    }

    @PreAuthorize("hasAuthority('client:update')")
    @LogAnnotation(module = LogModule.UPDATE_CLIENT)
    @PutMapping
    @ApiOperation(value = "修改")
    public void update(@ApiParam(value = "参数：实体clientDetails", required = true) @RequestBody BaseClientDetails clientDetails) {
        getAndCheckClient(clientDetails.getClientId(), true);
        clientDetailsService.updateClientDetails(clientDetails);
        log.info("修改client信息：{}", clientDetails);
    }

    @PreAuthorize("hasAuthority('client:update')")
    @LogAnnotation(module = LogModule.RESET_PASSWORD_CLIENT)
    @PutMapping(value = "/{clientId}", params = "secret")
    @ApiOperation(value = "根据client Id修改密码")
    public void updateSecret(@ApiParam(value = "clientId", required = true) @PathVariable String clientId,
                             @ApiParam(value = "新密码", required = true) String secret) {
        getAndCheckClient(clientId, true);
        checkSystemClient(clientId);

        secret = bCryptPasswordEncoder.encode(secret);
        clientDetailsService.updateClientSecret(clientId, secret);
        log.info("修改client密码：{},{}", clientId, secret);
    }

    @PreAuthorize("hasAuthority('client:query')")
    @GetMapping
    @ApiOperation(value = "分页查询")
    public Page<ClientDetails> findClients() {
        List<ClientDetails> clientDetails = clientDetailsService.listClientDetails();
        clientDetails.parallelStream().forEach(c -> isSystemClient(c));
        return new Page<>(clientDetails.size(), clientDetails);
    }

    @ApiOperation(value = "根据client Id 查询")
    @PreAuthorize("hasAuthority('client:query')")
    @GetMapping("/{clientId}")
    public ClientDetails getById(@ApiParam(value = "clientId", required = true)@PathVariable String clientId) {
        return getAndCheckClient(clientId, true);
    }

    @PreAuthorize("hasAuthority('client:del')")
    @LogAnnotation(module = LogModule.DELETE_CLIENT)
    @DeleteMapping("/{clientId}")
    @ApiOperation(value = "根据client Id删除")
    public void delete(@ApiParam(value = "clientId", required = true)@PathVariable String clientId) {
        getAndCheckClient(clientId, true);
        checkSystemClient(clientId);

        clientDetailsService.removeClientDetails(clientId);
        log.info("删除client：{}", clientId);
    }

    /**
     * 根据id获取client信息
     *
     * @param clientId
     * @param check    是否校验存在性
     * @return
     */
    private ClientDetails getAndCheckClient(String clientId, boolean check) {
        ClientDetails clientDetails = null;
        try {
            clientDetails = clientDetailsService.loadClientByClientId(clientId);
            isSystemClient(clientDetails);
        } catch (NoSuchClientException e) {
            if (check) {
                throw new IllegalArgumentException(clientId + "不存在");
            }
        }

        return clientDetails;
    }

    private void checkSystemClient(String clientId) {
        if (SystemClientInfo.CLIENT_ID.equals(clientId)) {
            throw new IllegalArgumentException("不能操作系统数据");
        }
    }

    /**
     * 判断是否是我们自己系统内部用的client<br>
     * 在扩展字段里放一个isSystem标注一下
     *
     * @param clientDetails
     * @see SystemClientInfo
     */
    private boolean isSystemClient(ClientDetails clientDetails) {
        BaseClientDetails baseClientDetails = (BaseClientDetails) clientDetails;
        Map<String, Object> additionalInformation = baseClientDetails.getAdditionalInformation();
        if (additionalInformation == null) {
            additionalInformation = new HashMap<>();
            baseClientDetails.setAdditionalInformation(additionalInformation);
        }

        boolean isSystem = SystemClientInfo.CLIENT_ID.equalsIgnoreCase(baseClientDetails.getClientId());
        baseClientDetails.addAdditionalInformation("isSystem", isSystem);

        return isSystem;
    }

}
