package com.food.basic.admin.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.common.dto.Criteria;
import com.food.basic.user.UserVO;

public interface AdminUserMapper {
	
	//메일저장
	void mailing_save(MailMngVO vo);
	//메일주소
	String[] getAllMailAddress();
	//메일 보낸 횟수
	void mailSendCountUpdate(int idx);
	//메일목록
	List<MailMngVO> getMailInfoList(@Param("cri") Criteria cri, @Param("title") String title);
	//목록 총개수
	int getMailListCount(String title);
	//메일정보
	MailMngVO getMailSendInfo(int idx);
	//메일수정
	void mailingedit(MailMngVO vo);
	//회원목록
	List<UserVO> user_list(Criteria cri);
	//총 회원수
	int userCount(Criteria cri);
	//회원정보
	UserVO user_info(String u_id);
	//회원탈퇴
	void user_delete(String u_id);
}
