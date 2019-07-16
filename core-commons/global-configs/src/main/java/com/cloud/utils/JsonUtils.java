package com.cloud.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * Json工具类:fast-json版
 */
@Slf4j
public class JsonUtils {
	public static final int SUCCESS=1;
	public static final int ERROR=0;
	public static final int FAIL=2;
	public static final String SUCCESS_MSG="操作成功";
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); // 使用和json-lib兼容的日期输出格式
    }

    private static final SerializerFeature[] features = {
    		SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };
    

    /**
     * 将javaBean、Map、List、JSONObject等对象转换为JSON字符串
     */
    public static String toJson(Object object) {
        return JSON.toJSONString(object, config, features);
    }
    
    /**
     * 将JSON字符串转换为JavaBean、Map
     * @param text JSON字符串
     * @param clazz 目标类型
     */
    public static <T> T toObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * 将JSON字符串转换为List
     * @param text JSON字符串
     * @param clazz 目标类型
     */
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }
    
    /**
	 * 创建结果对象
	 */
	public static JSONObject createResultMap(){
		JSONObject resultMap=new JSONObject();
		resultMap.put("code", SUCCESS);
		resultMap.put("msg", SUCCESS_MSG);
		resultMap.put("transport", null);
		return resultMap;
	}
	
	/**
	 * 以文本字符串形式写入
	 */
	public static void writeToText(HttpServletResponse response,String text) {
		try {
			response.setContentType("text/plain; charset=UTF-8");
			PrintWriter out =response.getWriter();
			out.write(text);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 以HTML元素形式写入
	 */
	public static void writeToHtml(HttpServletResponse response,String text) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out =response.getWriter();
			out.write(text);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 以JSON形式写入
	 */
	public static void writeToJson(HttpServletResponse response,String text) {
		try {
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out =response.getWriter();
			out.write(text);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void transportAsJson(HttpServletResponse response, JSONObject json) {
		String result=JsonUtils.toJson(json);
		log.info("json:"+result);
		JsonUtils.writeToHtml(response, result);
	}
	
	/**
	 * 将字符串转成类似map的JSONObject
	 * @param json
	 * @return
	 */
	public static JSONObject toJSONObject(String json){
		return JSON.parseObject(json);
	}
}
