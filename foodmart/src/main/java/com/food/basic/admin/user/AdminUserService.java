package com.food.basic.admin.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AdminUserService {
	
	private final AdminUserMapper adminUserMapper;
	
	public void mailing_save(MailMngVO vo) {
		
		adminUserMapper.mailing_save(vo);
	}
	
	public String[] getALLMailAddress() {
		
		return adminUserMapper.getAllMailAddress();
	}
	
	public void mailSendCountUpdate(int idx) {
		
		adminUserMapper.mailSendCountUpdate(idx);
	}
	
	public List<MailMngVO> getMailInfoList(Criteria cri, String title) {
		
		return adminUserMapper.getMailInfoList(cri, title);
	}
	
	public int getMailListCount(String title) {
		
		return adminUserMapper.getMailListCount(title);
	}
	
	public MailMngVO getMailSendInfo(int idx) {
		
		return adminUserMapper.getMailSendInfo(idx);
	}
	
	public void mailingedit(MailMngVO vo) {
		
		adminUserMapper.mailingedit(vo);
	}
}
