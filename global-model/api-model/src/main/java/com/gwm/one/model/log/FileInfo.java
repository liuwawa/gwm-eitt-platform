package com.gwm.one.model.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "file_info")
@ApiModel(value = "FileInfo", description = "文件信息")
public class FileInfo implements Serializable {

	private static final long serialVersionUID = -1438078028040922174L;

	/** 文件的md5 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "文件信息id")
	private String id;
	/** 原始文件名 */
	@ApiModelProperty(value = "原始文件名")
	private String name;
	/** 是否是图片 */
	@ApiModelProperty(value = "是否是图片")
	private Boolean isImg;
	@ApiModelProperty(value = "")
	private String contentType;
	@ApiModelProperty(value = "文件大小")
	private long size;
	@ApiModelProperty(value = "物理路劲")
	private String path;
	@ApiModelProperty(value = "网络url")
	private String url;
	/**
	 * 文件来源
	 * 
	 * @see FileSource
	 */
	@ApiModelProperty(value = "文件存储地方")
	private String source;
	private Date createTime;
}
