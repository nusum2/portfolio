package com.food.basic.admin.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.common.dto.Criteria;

public interface AdminUserMapper {
	
	void mailing_save(MailMngVO vo);
	
	String[] getAllMailAddress();
	
	void mailSendCountUpdate(int idx);
	
	List<MailMngVO> getMailInfoList(@Param("cri") Criteria cri, @Param("title") String title);
	
	int getMailListCount(String title);
	
	MailMngVO getMailSendInfo(int idx);
	
	void mailingedit(MailMngVO vo);
}
