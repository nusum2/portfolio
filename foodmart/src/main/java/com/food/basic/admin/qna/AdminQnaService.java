package com.food.basic.admin.qna;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;
import com.food.basic.qna.QnaVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Slf4j
@Service
public class AdminQnaService {
	
	private final AdminQnaMapper adminQnaMapper;
	
	public void qna_write(QnaVO vo) {
		
		adminQnaMapper.qna_write(vo);
	}
	
	public List<QnaVO> qna_list(Criteria cri) {
		
		return adminQnaMapper.qna_list(cri);
	}
	
	public int getTotalCount(Criteria cri) {
		
		return adminQnaMapper.getTotalCount(cri);
	}
	
	public QnaVO qna_content(int q_num) {
		
		return adminQnaMapper.qna_content(q_num);
	}
	
	public void qna_update(QnaVO vo) {
		
		adminQnaMapper.qna_update(vo);
	}
	
	public void qna_delete(int q_num) {
		
		adminQnaMapper.qna_delete(q_num);
	}
}
