package com.food.basic.kakaologin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Slf4j
@Service
public class KakaoLoginService {
	
	private final KakaoMapper kakaoMapper;
	
	@Value("${kakao.client.id}")
	private String clientId;
	
	@Value("${kakao.redirect.uri}")
	private String redirectUri;
	
	@Value("${kakao.client.secret}")
	private String clientSecret;
	
	@Value("${kakao.oauth.tokenuri}")
	private String tokenuri;
	
	@Value("${kakao.oauth.userinfouri}")
	private String userinfoUri;
	
	@Value("${kakao.user.logout}")
	private String kakaologout;
	
	//https://kauth.kakao.com/oauth/token 주소 호출
	//post
	//Content-type: application/x-www-form-urlencoded;charset=utf-8
	/*
	grant_type : authorization_code
	client_id : 앱 rest api 키
	redirect_uri : 인가코드 리다이렉트
	code : 인가 코드
	client_secret : 보안 강화 코드	
	
	*/
	public String getAccessToken(String code) throws JsonProcessingException{
		
		//1.헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//2.바디 생성
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("redirect_uri", redirectUri);
		body.add("code", code);
		body.add("client_secret", clientSecret);
		
		//3.헤더 + 바디로 엔티티 구성
		HttpEntity<MultiValueMap<String, String>> tokenKakaoRequest = new HttpEntity<MultiValueMap<String, String>>(body, headers);
		
		//4.Http 요청보내기 (API Server와 통신을 담당하는 기능을 제공하는 클래스) https://adjh54.tistory.com/234#google_vignette
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> response = restTemplate.exchange(tokenuri, HttpMethod.POST, tokenKakaoRequest, String.class);
		
		//5.Http 응답(JSON) -> Access Token 추출(Parsing)작업
		String responseBody = response.getBody();
		
		log.info("응답데이터 : " + responseBody);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		
		
		return jsonNode.get("access_token").asText();
	}
	//액세스 토큰을 이용한 사용자 정보 받아오기
	public KakaoUserInfo getKakaoUserInfo(String accessToken) throws JsonProcessingException {
		
		//1.헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//2.바디 생성안함. API 메뉴얼에서 필수가 아님.
		
		//3.헤더 + 바디로 엔티티 구성
		HttpEntity<MultiValueMap<String, String>> userInfoKakaoRequest = new HttpEntity<>(headers);
		
		//4.Http 요청보내기
		RestTemplate restTemplate = new RestTemplate();
		
		//5.Http 응답
		ResponseEntity<String> responseEntity = restTemplate.exchange(userinfoUri, HttpMethod.POST, userInfoKakaoRequest, String.class);
		
		String responseBody = responseEntity.getBody();
		
		log.info("응답 사용자 정보 : " + responseBody);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		//인증토큰을 이용한 카카오 사용자에 대한 정보를 받아옴
		Long id = jsonNode.get("id").asLong();
		String email = jsonNode.get("kakao_account").get("email").asText();
		String nickname = jsonNode.get("properties").get("nickname").asText();
		
		return new KakaoUserInfo(id, nickname, email);
	}
	
	//카카오 로그아웃 https://kapi.kakao.com/v1/user/logout 헤더는 있고 파라미터는 없는 경우
	//헤더 Authorization: Bearer ${ACCESS_TOKEN}
	public void kakaologout(String accessToken) throws JsonProcessingException {
		
		//http header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//http 요청작업
		HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
		
		//http 요청하기
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(kakaologout, HttpMethod.POST, kakaoLogoutRequest, String.class);
		
		//리턴된 정보 : json포맷의 문자열
		String responseBody = response.getBody();
		log.info("responseBody:" + responseBody);
		
		//JSON문자열을 java객체로 역직렬화 하거나 java객체를 json으로 직렬화 할때 사용하는 jackson 라이브러리 클래스
		//ObjectMapper 생성 비용이 비싸기때문에 bean/static으로 처리하는 것이 성능에 좋다.
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		Long id = jsonNode.get("id").asLong();
		
		log.info("id : " + id);
	}
}

