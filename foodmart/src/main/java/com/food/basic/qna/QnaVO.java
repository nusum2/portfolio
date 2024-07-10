package com.food.basic.qna;
/*
q_num number, --번호
q_title varchar2(50) not null, --제목
q_content varchar2(1000) not null, --내용
q_writer varchar2(15) not null, --글쓴이
q_regdate date default sysdate --날짜

*/

import java.util.Date;

import lombok.Data;
@Data
public class QnaVO {
	
	private int q_num;
	private String q_title;
	private String q_content;
	private String q_writer;
	private Date q_regdate;
	private Date q_updatedate;
}
