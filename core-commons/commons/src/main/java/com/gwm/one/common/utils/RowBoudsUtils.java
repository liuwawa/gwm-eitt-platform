package com.gwm.one.common.utils;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.RowBounds;

public class RowBoudsUtils {

    public static RowBounds getRowBounds(Integer pageIndex, Integer pageSize) {
        Integer offset = 0;
        Integer limit = pageSize;
        if (pageIndex != 1) {
            offset = (pageIndex - 1) * pageSize;
        }
        return new PageRowBounds(offset, limit);
    }

    public static PageRowBounds getPageRowBounds(Integer pageIndex, Integer pageSize) {
        Integer offset = 0;
        Integer limit = pageSize;
        if (pageIndex != 1) {
            offset = (pageIndex - 1) * pageSize;
        }
        return new PageRowBounds(offset, limit);
    }
}
