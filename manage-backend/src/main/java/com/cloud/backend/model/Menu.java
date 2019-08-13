package com.cloud.backend.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ExampleProperty;
import lombok.Data;

@Data
@ApiModel(value = "Menu", description = "菜单类")
public class Menu implements Serializable {

	private static final long serialVersionUID = 749360940290141180L;

	@ApiModelProperty(value = "菜单id")
	private Long id;
	@ApiModelProperty(value = "上级菜单id")
	private Long parentId;

	@TableField(exist = false)
	private String parentName;
	@ApiModelProperty(value = "菜单名字")
	private String name;
	@ApiModelProperty(value = "图标")
	private String css;
	@ApiModelProperty(value = "url")
	private String url;
	@ApiModelProperty(value = "排序")
	private Integer sort;
	private Date createTime;
	private Date updateTime;

	@ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮")
	private Integer type;

	private Integer delFlag;

	private List<Menu> children;

	private List<Menu> child;
}
