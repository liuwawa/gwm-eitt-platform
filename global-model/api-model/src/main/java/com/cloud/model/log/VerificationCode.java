package com.cloud.model.log;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerificationCode implements Serializable {

	private static final long serialVersionUID = -3122215341764978293L;

	private String key;
}
