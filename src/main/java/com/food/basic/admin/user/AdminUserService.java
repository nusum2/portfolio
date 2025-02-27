package com.food.basic.admin.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;
import com.food.basic.user.UserVO;

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
	
	public List<UserVO> user_list(Criteria cri) {
		
		return adminUserMapper.user_list(cri);
	}
	
	public int userCount(Criteria cri) {
		
		return adminUserMapper.userCount(cri);
	}
	
	public UserVO user_info(String u_id) {
		
		return adminUserMapper.user_info(u_id);
	}
	
	public void user_delete(String u_id) {
		
		adminUserMapper.user_delete(u_id);
	}
}
