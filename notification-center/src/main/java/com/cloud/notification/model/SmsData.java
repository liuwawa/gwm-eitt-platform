package com.cloud.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsData {
	private String msgid;
	private String phones;
	private String content;
	private String sign;
	private String subcode;
	private String sendtime;
}
