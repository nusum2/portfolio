package com.food.basic.admin.review;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;
import com.food.basic.review.ReviewVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminReviewService {

	private final AdminReviewMapper adminReviewMapper;
	
	public List<ReviewVO> re_list(Integer pro_num, Criteria cri) {
		
		return adminReviewMapper.re_list(pro_num, cri);
	}

	public int re_count(Integer pro_num) {
		
		return adminReviewMapper.re_count(pro_num);
	}
	
	public void review_delete(Long re_code) {
		
		adminReviewMapper.review_delete(re_code);
	}
	
	public ReviewVO review_detail(Long re_code) {
		
		return adminReviewMapper.review_detail(re_code);
	}
}
