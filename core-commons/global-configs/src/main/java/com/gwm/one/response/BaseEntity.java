package com.gwm.one.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求数据最外层封装对象
 * @author user wp
 */
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
