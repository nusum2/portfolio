package com.food.basic.user;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	
	void join(UserVO vo);
	
	String idCheck(String u_id);
	
	String nickCheck(String u_nick);
	
	UserVO login(String u_id);
	
	String idfind(@Param("u_name") String u_name, @Param("u_email") String u_email);
	
	String pwfind(@Param("u_id")String u_id ,@Param("u_name") String u_name, @Param("u_email")String u_email);
	
	void tempPwUpdate(@Param("u_id") String u_id, @Param("enc_pw") String enc_pw);
	
	void modify(UserVO vo);
	
	void changePw(@Param("u_id") String u_id, @Param("new_pwd") String new_pwd);
	
	void delete(String u_id);
	
	String existsUserInfo(String sns_email);
	
	//sns user 데이터삽입
	String sns_user_check(String sns_email);
	
	void sns_user_insert(SNSUserDto dto);
	
}
