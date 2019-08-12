package com.cloud.model.mail;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Mail", description = "邮件信息")
public class Mail implements Serializable {

    private static final long serialVersionUID = 4885093464493499448L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "邮件id")
    private Long id;
    @TableField("userId")
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**
     * 发送人用户名
     */
    @TableField("username")
    @ApiModelProperty(value = "发送人用户名")
    private String username;
    /**
     * 收件人邮件地址
     */
    @TableField("toEmail")
    @ApiModelProperty(value = "收件人邮件地址")
    private String toEmail;
    /**
     * 标题
     */
    @TableField("subject")
    @ApiModelProperty(value = "标题")
    private String subject;
    /**
     * 正文
     */
    @TableField("content")
    @ApiModelProperty(value = "正文")
    private String content;

    /*
     * 是否已读
     * */
    @TableField("isRead")
    @ApiModelProperty(value = "是否已读")
    private Integer isRead;
    /**
     * 发送时间
     */
    @TableField("sendTime")
    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

    /**
     * 发送状态
     *
     * @see com.cloud.model.mail.constants.MailStatus
     */
    @TableField("status")
    @ApiModelProperty(value = "发送状态")
    private Integer status;

    
    private Date createTime;
    private Date updateTime;

}
