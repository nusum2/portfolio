package com.food.basic.qna;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Slf4j
@Service
public class QnaService {
	
	private final QnaMapper qnaMapper;
	
	public void qna_write(QnaVO vo) {
		
		qnaMapper.qna_write(vo);
	}
	
	public List<QnaVO> qna_list(Criteria cri) {
		
		return qnaMapper.qna_list(cri);
	}
	
	public int getTotalCount(Criteria cri) {
		
		return qnaMapper.getTotalCount(cri);
	}
	
	public QnaVO qna_content(int q_num) {
		
		return qnaMapper.qna_content(q_num);
	}
	
	public void qna_update(QnaVO vo) {
		
		qnaMapper.qna_update(vo);
	}
	
	public void qna_delete(int q_num) {
		
		qnaMapper.qna_delete(q_num);
	}
}
