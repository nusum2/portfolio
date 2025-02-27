package com.food.basic.faq;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Slf4j
@Service
public class FaqService {
	
	private final FaqMapper faqMapper;
	
	public List<FaqVO> faq_list(Criteria cri) {
		
		return faqMapper.faq_list(cri);
	}
	
	public int getTotalCount(Criteria cri) {
		
		return faqMapper.getTotalCount(cri);
	}
	
}
