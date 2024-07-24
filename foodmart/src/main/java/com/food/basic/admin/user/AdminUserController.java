package com.food.basic.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin/user/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminUserController {
	
	private final AdminUserService adminUserService;
	
	//https://www.onedaynet.co.kr 참고
	
	//회원목록
	
	//회원조회 및 수정
	
	//메일발송 목록
	
	//매일발송 폼 (CKEditor 사용) - 구분 1.광고/이벤트 2.일반
	
	//메일발송 액션
}
