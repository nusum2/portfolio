package com.food.basic.review;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	
	private final ReviewMapper reviewMapper;
	
	public List<ReviewVO> re_list(Integer pro_num, Criteria cri) {
		
		return reviewMapper.re_list(pro_num, cri);
	}
	
	public int re_count(Integer pro_num) {
		
		return reviewMapper.re_count(pro_num);
	}
	
	public void review_save(ReviewVO vo) {
		
		reviewMapper.review_save(vo);
	}
	
	public void review_delete(Long re_code) {
		
		reviewMapper.review_delete(re_code);
	}
	
	public ReviewVO review_modify(Long re_code) {
		
		return reviewMapper.review_modify(re_code);
	}
	
	public void review_update(ReviewVO vo) {
		
		reviewMapper.review_update(vo);
	}
	
	public ReviewVO review_detail(Long re_code) {
		
		return reviewMapper.review_detail(re_code);
	}
}
