package com.gwm.one.hr.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gwm.one.model.hr.user.WechatUserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface WechatDao extends BaseMapper<WechatUserInfo> {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into t_wechat(openid, unionid, userId, app, nickname, sex, province, city, country, headimgurl, createTime, updateTime) " +
            "values(#{openid}, #{unionid}, #{userId}, #{app}, #{nickname}, #{sex}, #{province}, #{city}, #{country}, #{headimgurl}, #{createTime}, #{updateTime})")
    int save(WechatUserInfo info);

    @Select("select * from t_wechat t where t.openid = #{openid}")
    WechatUserInfo findByOpenid(String openid);

    @Select("select * from t_wechat t where t.unionid = #{unionid}")
    Set<WechatUserInfo> findByUniond(String unionid);

    int update(WechatUserInfo info);
}
