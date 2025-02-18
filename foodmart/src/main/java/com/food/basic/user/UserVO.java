package com.food.basic.user;

import java.util.Date;

import lombok.Data;

/*
 u_id             VARCHAR2(15), --아이디
 u_NAME           VARCHAR2(30)            NOT NULL, --이름
 u_EMAIL          VARCHAR2(50)            NOT NULL, --이메일
 u_PASSWORD       CHAR(60)               NOT NULL,        -- 비밀번호 암호화 처리.
 u_NICK           VARCHAR2(30)            NOT NULL UNIQUE, --닉네임
 u_ZIPCODE        CHAR(5)                 NOT NULL, --우편번호
 u_ADDR           VARCHAR2(100)            NOT NULL, --주소
 u_DEADDR         VARCHAR2(100)            NOT NULL, --상세주소
 u_PHONE          VARCHAR2(15)            NOT NULL, --전화번호
 u_receive        char(1) default 'Y'       not null, --메일 수신 동의 여부
 u_regdate        DATE DEFAULT SYSDATE    NOT NULL, --가입날짜
 u_update date default sysdate not null, --수정일
 u_lastlogin date null,
*/
@Data
public class UserVO {
	
	private String u_id;
	private String u_name;
	private String u_email;
	private String u_password;
	private String u_zipcode;
	private String u_addr;
	private String u_deaddr;
	private String u_phone;
	private String u_receive;
	private Date u_regdate;
	private Date u_lastlogin;
}
