package com.cloud.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Redis 工具
 */
@Component
@Slf4j
public class RedisUtils {

    /** 字符串缓存模板 */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /** 对象，集合缓存模板  */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;



    public void reset(String key, Long seconds){
        stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取匹配的key
     * @param pattern
     * @return Set<String>
     */
    public Set<String> keys(String pattern){
        return stringRedisTemplate.keys(pattern);
    }

    /**
     * 批量删除keys
     * @param pattern
     */
    public void delKeys(String pattern){
        redisTemplate.delete(stringRedisTemplate.keys(pattern));
    }

    /**
     * 添加Set集合
     * @param key
     * @param set
     */
    public void addSet(String key ,Set<?> set){
        try{
            redisTemplate.opsForSet().add(key, set);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 获取Set集合
     * @param key
     * @return
     */
    public Set<?> getSet(String key){
        try{
            return redisTemplate.opsForSet().members(key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null ;
    }




    public  void addString(String key , String value,Long seconds){

        try {
            // 字符串redis 存储
            ValueOperations<String, String> valOps = stringRedisTemplate.opsForValue();
            if(seconds!=null){
                valOps.set(key, value,seconds,TimeUnit.SECONDS);
            }else{
                valOps.set(key, value);
            }
        } catch (Exception e) {
            log.warn(spellString("addString {0}={1},{2}", key,value,seconds),e);
        }
    }




    public String getString(String key) {
        String result = "";
        try {
            result = stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.warn(spellString("getString {0}", key), e);
        }
        return result;
    }


    public  void delString(String key) {
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.warn(spellString("delString {0}", key),e);
        }
    }



    public void delAllString(String key) {
        if(key==null || "".equals(key)){
            return;
        }
        try {
            if (!key.endsWith("*")) {
                key += "*";
            }
            Set<String> keys = stringRedisTemplate.keys(key);
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String singleKey = it.next();
                delString(singleKey);
            }
        } catch (Exception e) {
            log.warn(spellString("delString {0}", key), e);
        }
    }



    /**
     * @see   add 缓存数据
     * @param key
     * @param value
     * @param time
     */
    public void addObj(String key ,Object obj, Long seconds){
        try {
            //对象redis存储
            ValueOperations<Object, Object> objOps = redisTemplate.opsForValue();
            if(seconds!=null){
                objOps.set(key, obj, seconds, TimeUnit.SECONDS);
            }else{
                objOps.set(key, obj);
            }
        } catch (Exception e) {
            log.warn(spellString("addObj {0}={1},{2}", key,obj,seconds),e);
        }
    }


    /**
     * @see    get 缓存数据
     * @param  key
     * @return Object
     */
    public Object getObject(String key) {
        Object object = null;
        try {
            object = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.warn(spellString("getObj {0}", key), e);
        }
        return object;
    }



    /**
     * @see    get 缓存数据
     * @param  key
     * @return Object
     */
    @SuppressWarnings({ "unchecked"})
    public <T> T getObj(String key, T t) {
        Object o = null;
        try {
            o = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.warn(spellString("getObj {0}->{1}", key, t), e);
        }
        return o == null ? null : (T) o;
    }



    public void expire(String key,long second){
        try {
            stringRedisTemplate.expire(key, second, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.warn(spellString("expire {0}={1}", key, second),e);
        }
    }

    /**
     * @see   del 缓存数据
     * @param key
     */
    public void delObj(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.warn(spellString("delObj {0}", key),e);
        }
    }



    /**
     * 压栈
     *
     * @param key
     * @param value
     * @return
     */
    public Long push(String key, String value) {
        Long result = 0l;
        try {
            result = stringRedisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            log.warn(spellString("push {0}={1}", key,value),e);
        }
        return result;
    }

    /**
     * 出栈
     *
     * @param key
     * @return
     */
    public String pop(String key) {
        String popResult = "";
        try {
            popResult = stringRedisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.warn(spellString("pop {0}", key), e);
        }
        return popResult;
    }

    /**
     * 入队
     *
     * @param key
     * @param value
     * @return
     */
    public Long in(String key, String value) {
        Long inResult = 0l;
        try {
            inResult = stringRedisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.warn(spellString("in {0}={1}", key, value), e);
        }
        return inResult;
    }

    /**
     * 出队
     *
     * @param key
     * @return
     */
    public String out(String key) {
        String outResult = "";
        try {
            outResult = stringRedisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.warn(spellString("out {0}", key),e);
        }
        return outResult;
    }

    /**
     * 栈/队列长
     *
     * @param key
     * @return
     */
    public Long length(String key) {
        Long lengthResult = 0l;
        try {
            lengthResult = stringRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.warn(spellString("length {0}", key), e);
        }
        return lengthResult;
    }

    /**
     * 范围检索
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> range(String key, int start, int end) {
        List<String> rangeResult = null;
        try {
            rangeResult = stringRedisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.warn(spellString("range {0},{1}-{2}", key, start, end), e);
        }
        return rangeResult;
    }

    /**
     * 移除
     *
     * @param key
     * @param i
     * @param value
     */
    public void remove(String key, long i, String value) {
        try {
            stringRedisTemplate.opsForList().remove(key, i, value);
        } catch (Exception e) {
            log.warn(spellString("remove {0}={1},{2}", key,value,i),e);
        }
    }

    /**
     * 检索
     *
     * @param key
     * @param index
     * @return
     */
    public String index(String key, long index) {
        String indexResult = "";
        try {
            indexResult = stringRedisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.warn(spellString("index {0}", key), e);
        }
        return indexResult;
    }

    /**
     * 置值
     *
     * @param key
     * @param index
     * @param value
     */
    public void setObject(String key,  Object value,long index) {
        try {
            redisTemplate.opsForValue().set(key,value,index);
        } catch (Exception e) {
            log.warn(spellString("set {0}={1},{2}", key,value,index),e);
        }
    }


    public boolean setString(String key,String value ){
        try {
            stringRedisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }





    /**
     * 裁剪
     *
     * @param key
     * @param start
     * @param end
     */
    public void trim(String key, long start, int end) {
        try {
            stringRedisTemplate.opsForList().trim(key, start, end);
        } catch (Exception e) {
            log.warn(spellString("trim {0},{1}-{2}", key,start,end),e);
        }
    }

    /**
     * 方法说明: 原子性自增
     * @param key 自增的key
     * @param value 每次自增的值
     * @time: 2017年3月9日 下午4:28:21
     * @return: Long
     */
    public Long incr(String key, long value) {
        Long incrResult = 0L;
        try {
            incrResult = stringRedisTemplate.opsForValue().increment(key, value);
        } catch (Exception e) {
            log.warn(spellString("incr {0}={1}", key, value), e);
        }
        return incrResult;
    }

    /**
     * 拼异常内容
     * @param errStr
     * @param arguments
     * @return
     */
    private String spellString(String errStr,Object ... arguments){
        return MessageFormat.format(errStr,arguments);
    }
}