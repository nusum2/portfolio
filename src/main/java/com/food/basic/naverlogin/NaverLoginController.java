package com.food.basic.naverlogin;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.basic.user.SNSUserDto;
import com.food.basic.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/oauth2")
@Slf4j
@Controller
public class NaverLoginController {
	
	private final NaverLoginService naverLoginService;
	private final UserService userService;
	
	//스텝1
	@GetMapping("naverlogin")
	public String connect() throws UnsupportedEncodingException{
		
		String url = naverLoginService.getNaverAuthorizeUri();
		
		return "redirect:" + url;
	}
	
	//스텝2
	//callback 주소 생성작업 http://localhost:9090/oauth2/callback/naver
	//api 요청 성공시 : http://콜백url/redirect?code={code값}&state
	//
	@GetMapping("/callback/naver")
	public String callBack(NaverCallback callback, HttpSession session) throws Exception {
		
		if(callback.getError() != null) {
			log.info(callback.getError_description());
		}
		//json포맷 응답데이터
		String responseToken = naverLoginService.getNaverTokenUrl(callback);
		
		ObjectMapper objectMapper = new ObjectMapper();
		NaverToken naverToken = objectMapper.readValue(responseToken, NaverToken.class);
		
		log.info("토큰정보 : " + naverToken.toString());
		
		//액세스토큰을 이용한 사용자 정보 받아오기
		String responseUser = naverLoginService.getNaverUserByToken(naverToken);
		NaverResponse naverResponse = objectMapper.readValue(responseUser, NaverResponse.class);
		
		log.info("사용자정보 : " + naverResponse.toString());
		
		String sns_email = naverResponse.getResponse().getEmail();
		
		if(naverResponse != null) {
			session.setAttribute("naver_status", naverResponse);
			session.setAttribute("accessToken", naverToken.getAccess_token());
			
			log.info("네이버 계정 정보 : " + naverResponse);
			
			if(userService.existsUserInfo(sns_email) == null && userService.sns_user_check(sns_email) == null) {
				SNSUserDto dto = new SNSUserDto();
				dto.setId(naverResponse.getResponse().getId());
				dto.setEmail(naverResponse.getResponse().getEmail());
				dto.setName(naverResponse.getResponse().getName());
				dto.setSns_type("naver");
				
				userService.sns_user_insert(dto);
			}
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/naverlogout")
	public String naverlogout(HttpSession session) {
		
		String accessToken = (String) session.getAttribute("accessToken");
		
		naverLoginService.getNaverTokenDelete(accessToken);
		
		if(accessToken != null && !"".equals(accessToken)) {
//			try {
//				kakaoLoginService.kakaologout(accessToken);
//			}catch(JsonProcessingException ex) {
//				throw new RuntimeException();
//			}
			session.removeAttribute("naver_status");
			session.removeAttribute("accessToken");
		}
		
		return "redirect:/";
	}
	
}
