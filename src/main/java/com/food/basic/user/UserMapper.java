package com.food.basic.user;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	//회원가입
	void join(UserVO vo);
	//아이디 중복체크
	String idCheck(String u_id);
	//닉네임 중복체크
	//String nickCheck(String u_nick);
	//로그인
	UserVO login(String u_id);
	//아이디찾기
	String idfind(@Param("u_name") String u_name, @Param("u_email") String u_email);
	//비번찾기
	String pwfind(@Param("u_id")String u_id ,@Param("u_name") String u_name, @Param("u_email")String u_email);
	//임시 비밀번호
	void tempPwUpdate(@Param("u_id") String u_id, @Param("enc_pw") String enc_pw);
	//회원 정보 수정
	void modify(UserVO vo);
	//비번 변경
	void changePw(@Param("u_id") String u_id, @Param("new_pwd") String new_pwd);
	//탈퇴
	void delete(String u_id);
	//sns 유저 정보
	String existsUserInfo(String sns_email);
	//sns user 데이터삽입
	String sns_user_check(String sns_email);
	//sns 회원가입
	void sns_user_insert(SNSUserDto dto);
	//마지막 로그인 접속일
	void user_lastlogin(UserVO lastlogin);
}
