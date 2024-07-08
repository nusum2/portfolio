package com.food.basic.review;
/*
re_code number not null, --리뷰번호
u_id varchar2(15) not null, --유저아이디
pro_num number not null, --상품 번호
re_title varchar2(50) not null, --제목
re_content varchar2(200) not null, --내용
re_rate number not null, --점수
re_up_folder varchar2(50) not null, --이미지 폴더
re_img varchar2(50) not null, --이미지
re_date date default sysdate --날짜
  
 */

import java.util.Date;

import lombok.Data;
@Data
public class ReviewVO {
	
	private Long re_code;
	private String u_id;
	private int pro_num;
	private String re_title;
	private String re_content;
	private int re_rate;
	private String re_up_folder;
	private String re_img;
	private Date re_date;
}
