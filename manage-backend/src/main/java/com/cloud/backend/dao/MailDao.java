package com.cloud.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.model.mail.Mail;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MailDao extends BaseMapper<Mail> {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into t_mail(userId, username, toEmail, subject, content, status, createTime, updateTime) values(#{userId}, #{username}, #{toEmail}, #{subject}, #{content}, #{status}, #{createTime}, #{updateTime})")
    int save(Mail mail);

    int update(Mail mail);

    @Select("select * from t_mail t where t.id = #{id}")
    Mail findById(Long id);

    int count(Map<String, Object> params);

    List<Mail> findData(Map<String, Object> params);

    @Select("SELECT * FROM t_mail WHERE isRead=0 and userId=#{userId} ORDER BY sendTime DESC LIMIT 5")
    List<Mail> findNoRead(Long userId);

    @Update("UPDATE t_mail SET isRead=1 where id = #{id}")
    void updateIsRead(Long id);
}
