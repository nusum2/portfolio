package com.food.basic.admin.qna;

import java.util.List;

import com.food.basic.common.dto.Criteria;
import com.food.basic.qna.QnaVO;

public interface AdminQnaMapper {
	
	void qna_write(QnaVO vo);
	
	List<QnaVO> qna_list(Criteria cri);
	
    int getTotalCount(Criteria cri);
	
	QnaVO qna_content(int q_num);
	
	void qna_update(QnaVO vo);
	
	void qna_delete(int q_num);
}
