package com.gwm.one.model.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "VerificationCode", description = "发送验证码")
public class VerificationCode implements Serializable {

	private static final long serialVersionUID = -3122215341764978293L;

	@ApiModelProperty(value = "验证码")
	private String key;
}
