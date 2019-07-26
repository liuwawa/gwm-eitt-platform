package com.cloud.oauth.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lz
 * @create time  2018/3/30 20:37
 * @description
 * @modify
 * @modify time
 */
@Getter
@Setter
public class PageAndSortResponse extends BaseResponse {

    private Integer currentPage;
    private Integer pageSize;
    private long count;
    List items;

    protected PageAndSortResponse() {
    }

}
