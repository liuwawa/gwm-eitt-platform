package com.gwm.one.response;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * entity -> 实体
 * @author user wp
 */
@Slf4j
public class ObjectConversionEntityUtil {

    public static <T> T getObjectData(Object object, Class<T> entity) {
        try {
            String s = JSON.toJSONString(object);
            T o = JSON.parseObject(s, entity);
            return o;
        } catch (Exception e) {
            log.error(""+e);
            log.info("ERROR:请求当中并没有携带有效的Json参数！请仔细检查请求数据格式及方法所需参数!");
            return null;
        }
    }
    public static <T> T getBaseData(BaseEntity baseEntity, Class<T> entity) {
        try {
            String s = JSON.toJSONString(baseEntity.getRequestBody());
            T o = JSON.parseObject(s, entity);
            return o;
        } catch (Exception e) {
            log.error(""+e);
            log.info("ERROR:请求当中并没有携带有效的Json参数！请仔细检查请求数据格式及方法所需参数!");
            return null;
        }

    }
}
