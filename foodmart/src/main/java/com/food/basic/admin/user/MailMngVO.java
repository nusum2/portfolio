package com.food.basic.admin.user;

import java.util.Date;

import lombok.Data;

@Data
public class MailMngVO {
	
	private Integer idx;
	private String title;
	private String content;
	private String gubun;
	private int sendcount;
	private Date regDate;
}
