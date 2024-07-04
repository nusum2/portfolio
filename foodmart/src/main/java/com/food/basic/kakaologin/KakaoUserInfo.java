package com.food.basic.kakaologin;

import lombok.AllArgsConstructor;
import lombok.Data;
//카카오로부터 넘어오는 사용자 정보를 담당하는 클래스

@AllArgsConstructor
@Data
public class KakaoUserInfo {
	
	private Long id;
	private String nickname;
	private String email;
}
