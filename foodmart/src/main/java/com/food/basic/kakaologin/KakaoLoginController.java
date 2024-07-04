package com.food.basic.kakaologin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.basic.user.SNSUserDto;
import com.food.basic.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequestMapping("/oauth2")
@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoLoginController {
	
	private final KakaoLoginService kakaoLoginService;
	private final UserService userService;
	
	@Value("${kakao.client.id}")
	private String clientId;
	
	@Value("${kakao.redirect.uri}")
	private String redirectUri;
	
	@Value("${kakao.client.secret}")
	private String clientSecret;
	
	//스텝 1단계
	//카카오 로그인 api 서버에게 인가코드를 요청하는 작업
	@GetMapping("/kakaologin")
	public String connect() {
		
		StringBuffer url = new StringBuffer();
		url.append("https://kauth.kakao.com/oauth/authorize?");
		url.append("response_type=code");
		url.append("&client_id=" + clientId);
		url.append("&redirect_uri=" + redirectUri); //http://localhost:9090/oauth2/callback/kakao
		
		//추가옵션 다시 사용자 인증을 수행하고자 할 때 사용
		url.append("&prompt=login");
		
		log.info("인가코드 : " + url.toString());
		
		return "redirect:" + url.toString();
	}
	
	//스텝 2단계
	//카카오 로그인 api에서 callback호출. 카카오 개발자에서 애플리케이션 등록과정에서 아래주소로 설정을 이미 한 상태
	@GetMapping("/callback/kakao")
	public String callback(String code, HttpSession session) {
		
		log.info("code : " + code);
		
		String accessToken = "";
		KakaoUserInfo kakaoUserInfo = null;
		
		try {
			//카카오 로그인 api 서버에게 로그인 인증 성공, 인증토큰을 아용하여 카카오 사용자에 대한 정보를 제공
			accessToken = kakaoLoginService.getAccessToken(code); //인가코드를 통한 인증토큰을 요청
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		try {
			//카카오 로그인 api 서버에서 보내온 사용자 정보
			kakaoUserInfo = kakaoLoginService.getKakaoUserInfo(accessToken);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		if(kakaoUserInfo != null) {
			log.info("사용자정보 : " + kakaoUserInfo);
			
			//인증을 세션 방식으로 처리
			session.setAttribute("kakao_status", kakaoUserInfo); //인증여부에 사용
			session.setAttribute("accessToken", accessToken); //카카오 로그아웃에 사용
			
			String sns_email = kakaoUserInfo.getEmail();
			
			String sns_login_type = userService.existsUserInfo(sns_email);
			
			if(userService.existsUserInfo(sns_email) == null && userService.sns_user_check(sns_email) == null) {
				//sns 테이블에서 데이터 삽입작업
				SNSUserDto dto = new SNSUserDto();
				dto.setId(kakaoUserInfo.getId().toString());
				dto.setEmail(kakaoUserInfo.getEmail());
				dto.setName(kakaoUserInfo.getNickname());
				dto.setSns_type("kakao");
				
				userService.sns_user_insert(dto);
			}
		}
		
		return "redirect:/";
		
	}
	
	@GetMapping("/kakaologout")
	public String kakaologout(HttpSession session) {
		
		String accessToken = (String) session.getAttribute("accessToken");
		
		if(accessToken != null && !"".equals(accessToken)) {
			try {
				kakaoLoginService.kakaologout(accessToken);
			}catch(JsonProcessingException ex) {
				throw new RuntimeException();
			}
			session.removeAttribute("kakao_status");
			session.removeAttribute("accessToken");
		}
		
		return "redirect:/";
	}
}
