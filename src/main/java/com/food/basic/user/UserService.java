package com.food.basic.user;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserMapper userMapper;

	
	public void join(UserVO vo) {
		
		userMapper.join(vo);
	}
	
	public String idCheck(String u_id) {
		
		return userMapper.idCheck(u_id);
	}
	/*
	public String nickCheck(String u_nick) {
		
		return userMapper.nickCheck(u_nick);
	}
	*/
	public UserVO login(String u_id) {
		
		return userMapper.login(u_id);
	}
	
	public String idfind(String u_name, String u_email) {
		
		return userMapper.idfind(u_name, u_email);
	}
	
	public String pwfind(String u_id, String u_name, String u_email) {
		
		return userMapper.pwfind(u_id, u_name, u_email);
	}
	
	//임시비밀번호 10자리 생성
	public String getTempPw() {
		
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0,10);
	}
	
	
	public void tempPwUpdate(String u_id, String enc_pw) {
		
		userMapper.tempPwUpdate(u_id, enc_pw);
	}
	
	public void modify(UserVO vo) {
		
		userMapper.modify(vo);
	}
	
	public void changePw(String u_id, String new_pwd) {
		
		userMapper.changePw(u_id, new_pwd);
	}

	public void delete(String u_id) {
		
		userMapper.delete(u_id);
	}
	
	public String existsUserInfo(String sns_email) {
		
		return userMapper.existsUserInfo(sns_email);
	}
	
	public String sns_user_check(String sns_email) {
		
		return userMapper.sns_user_check(sns_email);
	}
	
	public void sns_user_insert(SNSUserDto dto) {
		
		userMapper.sns_user_insert(dto);
	}
	
	public void user_lastlogin(UserVO lastlogin) {
		
		userMapper.user_lastlogin(lastlogin);
	}
}
