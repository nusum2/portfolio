package com.food.basic.mail;

import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.food.basic.common.constants.Constants;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {
	
	//EmailConfig.java 파일의 JavaMailSender()메소드가 스프링에서 실행되어 리턴되는 타입의 객체
	//bean 생성 및 등록작업 후 아래 객체에 주입해준다.
	private final JavaMailSender mailSender;
	
	//타임리프 템플릿 사용용 필드
	private final SpringTemplateEngine templateEngine;
	
	
	public void sendMail(String type, EmailDTO dto, String authcode) {
		
		type = Constants.MAILFOLDERNAME + "/" + type;
		
		//메일 구성 정보 담당(받는사람, 보내는 사람, 받는 사람 메일주소, 본문내용)
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			//받는 사람의 메일주소
			//mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(dto.getReceiverMail()));
			
			//보내는 사람(메일, 이름)
			//mimeMessage.addFrom(new InternetAddress[] {new InternetAddress(dto.getSenderMail(), dto.getSenderName())});
			
			//제목
			//mimeMessage.setSubject(dto.getSubject(), "utf-8");
			
			//본문내용
			//mimeMessage.setText(authcode, "utf-8");
			
			//타임리프 사용용 코드 구성
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo(dto.getReceiverMail()); //메일수신자
			mimeMessageHelper.setFrom(new InternetAddress(dto.getSenderMail(), dto.getSenderName()));
			mimeMessageHelper.setSubject(dto.getSubject()); //메일 제목
			mimeMessageHelper.setText(setContext(authcode, type), true); //메일 본문 내용, HTML 여부
			
			//메일 발송기능
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMail(EmailDTO dto, String[] emailArr) {
		
		//메일구성정보 담당(받는사람, 보내는 사람, 받는사람 메일주소, 본문내용)
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			
			// 메일템플릿으로 타임리프 사용목적으로 아래코드가 구성됨.
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            
//			InternetAddress[] inetAddresses = InternetAddress.parse(dto.getReceiverMail());
			
			mimeMessageHelper.setTo(emailArr); // 메일 수신자
            
            
            
            mimeMessageHelper.setFrom(new InternetAddress(dto.getSenderMail(), dto.getSenderName()));
            mimeMessageHelper.setSubject(dto.getSubject()); // 메일 제목
            mimeMessageHelper.setText(dto.getMessage(), true); // 메일 본문 내용, HTML 여부
			
			// 메일발송기능
			mailSender.send(mimeMessage);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	// 인증번호 및 임시 비밀번호 생성 메서드
    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }
    
	//타임리프를 통한 html 적용
	//String authcode : 인증코드, String type : email.html
	public String setContext(String authcode, String type) {
		Context context = new Context();
		context.setVariable("authcode", authcode);
		
		return templateEngine.process(type, context);
	}
	
}
