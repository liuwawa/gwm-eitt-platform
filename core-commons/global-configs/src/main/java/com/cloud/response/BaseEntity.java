package com.cloud.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BaseEntity implements Serializable {
    //每页数量
    protected Integer pageSize = 15;
    //第几页
    protected Integer pageIndex = 1;
    //总页数
    protected Integer totalPage;
    //前端传输的数据
    protected Object requestBody;
    //token
    private String userToken;
    protected Integer randomLimit = 1000;
    protected Integer limit = 12;
}
