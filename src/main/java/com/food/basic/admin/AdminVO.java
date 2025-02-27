package com.food.basic.admin;
/*
ADMIN_ID    VARCHAR2(15)    PRIMARY KEY, --운영자 아이디

    ADMIN_PW    CHAR(60)    NOT NULL, --운영자 비밀번호

    ADMIN_VISIT_DATE    DATE default sysdate --접속일

*/

import java.util.Date;

import lombok.Data;

@Data
public class AdminVO {
	
	private String admin_id;
	private String admin_pw;
	private Date admin_visit_date;
}
