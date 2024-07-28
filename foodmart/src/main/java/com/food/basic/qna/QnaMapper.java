package com.food.basic.qna;

import java.util.List;

import com.food.basic.common.dto.Criteria;

public interface QnaMapper {
	
	List<QnaVO> qna_list(Criteria cri);
	
    int getTotalCount(Criteria cri);
	
}
