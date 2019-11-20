package com.gwm.one.dto.base;

import lombok.Data;

/**
 * Created by lz on 2018/6/26.
 */
@Data
public class BaseDto extends HistoryBase {
    //每页数量
    protected Integer pageSize = 15;
    //第几页
    protected Integer pageIndex = 1;
    //总页数
    protected Integer totalPage;
    //加密的token
    protected String encryptedToken;
    //明文token
    protected String token;
    //app用户的id
    //请求头 带三个字段
    protected Header header;

    protected String phone;

    protected String requestBody;

    protected Integer userId;

    protected Boolean needAuth;

    protected Integer randomLimit = 1000;

    protected Integer limit = 12;

}
