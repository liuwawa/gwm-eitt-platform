package com.cloud.model.mail;

import com.baomidou.mybatisplus.annotation.IdType;
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
    private Long userId;
    /**
     * 发送人用户名
     */
    private String username;
    /**
     * 收件人邮件地址
     */
    private String toEmail;
    /**
     * 标题
     */
    private String subject;
    /**
     * 正文
     */
    private String content;

    /*
     * 是否已读
     * */
    private Integer isRead;
    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 发送状态
     *
     * @see com.cloud.model.mail.constants.MailStatus
     */
    private Integer status;


    private Date createTime;
    private Date updateTime;

}
