package com.food.basic.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDTO {
	
	private String senderName; //발신자 이름
	private String senderMail; //발신자 메일주소
	private String receiverMail; //수신자 메일주소
	private String subject; //제목
	private String message; //내용
	
	public EmailDTO() {
		this.senderMail = "Foodmart";
		this.senderName = "Foodmart";
		this.subject = "Foodmart 회원가입 메일인증코드입니다.";
		this.message = "메일 인증코드를 확인하시고, 회원가입시 인증코드 입력란에 입력바랍니다.";
	}
}
