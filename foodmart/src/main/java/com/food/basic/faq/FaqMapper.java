package com.food.basic.faq;

import java.util.List;

import com.food.basic.common.dto.Criteria;

public interface FaqMapper {
	
	List<FaqVO> faq_list(Criteria cri);
	
    int getTotalCount(Criteria cri);
	
}
