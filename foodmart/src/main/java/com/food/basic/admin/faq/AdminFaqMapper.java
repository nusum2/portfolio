package com.food.basic.admin.faq;

import java.util.List;

import com.food.basic.common.dto.Criteria;
import com.food.basic.faq.FaqVO;

public interface AdminFaqMapper {
	
	void faq_write(FaqVO vo);
	
	List<FaqVO> faq_list(Criteria cri);
	
    int getTotalCount(Criteria cri);
	
	FaqVO faq_content(int q_num);
	
	void faq_update(FaqVO vo);
	
	void faq_delete(int q_num);
}
