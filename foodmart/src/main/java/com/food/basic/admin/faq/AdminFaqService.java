package com.food.basic.admin.faq;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;
import com.food.basic.faq.FaqVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Slf4j
@Service
public class AdminFaqService {
	
	private final AdminFaqMapper adminFaqMapper;
	
	public void faq_write(FaqVO vo) {
		
		adminFaqMapper.faq_write(vo);
	}
	
	public List<FaqVO> faq_list(Criteria cri) {
		
		return adminFaqMapper.faq_list(cri);
	}
	
	public int getTotalCount(Criteria cri) {
		
		return adminFaqMapper.getTotalCount(cri);
	}
	
	public FaqVO faq_content(int q_num) {
		
		return adminFaqMapper.faq_content(q_num);
	}
	
	public void faq_update(FaqVO vo) {
		
		adminFaqMapper.faq_update(vo);
	}
	
	public void faq_delete(int q_num) {
		
		adminFaqMapper.faq_delete(q_num);
	}
}
