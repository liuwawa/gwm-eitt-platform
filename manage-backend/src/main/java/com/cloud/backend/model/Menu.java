package com.cloud.backend.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Menu implements Serializable {

	private static final long serialVersionUID = 749360940290141180L;

	private Long id;
	private Long parentId;
	@TableField(exist = false)
	private String parentName;
	private String name;
	private String css;
	private String url;
	private Integer sort;
	private Date createTime;
	private Date updateTime;

	private Integer type;

	private Integer delFlag;

	private List<Menu> children;

	private List<Menu> child;
}
