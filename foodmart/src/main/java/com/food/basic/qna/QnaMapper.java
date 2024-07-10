package com.food.basic.qna;

import java.util.List;

import com.food.basic.common.dto.Criteria;

public interface QnaMapper {
	
	void qna_write(QnaVO vo);
	
	List<QnaVO> qna_list(Criteria cri);
	
    int getTotalCount(Criteria cri);
	
	QnaVO qna_content(int q_num);
	
	void qna_update(QnaVO vo);
}
