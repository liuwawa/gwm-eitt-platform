package com.cloud.model.mail;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件
 *
 * @author lz
 */
@Data
@TableName(value = "t_mail")
public class Mail implements Serializable {

    private static final long serialVersionUID = 4885093464493499448L;

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("userId")
    private Long userId;
    /**
     * 发送人用户名
     */
    @TableField("username")
    private String username;
    /**
     * 收件人邮件地址
     */
    @TableField("toEmail")
    private String toEmail;
    /**
     * 标题
     */
    @TableField("subject")
    private String subject;
    /**
     * 正文
     */
    @TableField("content")
    private String content;

    /*
     * 是否已读
     * */
    @TableField("isRead")
    private Integer isRead;
    /**
     * 发送时间
     */
    @TableField("sendTime")
    private Date sendTime;

    /**
     * 发送状态
     *
     * @see com.cloud.model.mail.constants.MailStatus
     */
    @TableField("status")
    private Integer status;

    
    private Date createTime;
    private Date updateTime;

}
