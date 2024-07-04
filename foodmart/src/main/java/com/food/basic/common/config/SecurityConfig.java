package com.food.basic.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //설정목적 클래스에는 해당 어노테이션 적용
//@EnableWebSecurity
public class SecurityConfig {
	
	//스프링시큐리티 설정, 2.7버전과 차이있음
//	@Bean
//	SecurityFilterChain cSecurityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf((csrf) -> csrf.disable());
//		//.cors((c) -> c.disable())
//		//.headers((headers) -> headers.disable());
//		return http.build();
//	}
	//암호화기능 bean생성
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
