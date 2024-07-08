package com.food.basic.review;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	
	private final ReviewMapper reviewMapper;
	
	public List<ReviewVO> re_list(int pro_num, Criteria cri) {
		
		return reviewMapper.re_list(pro_num, cri);
	}
	
	public int re_count(int pro_num) {
		
		return reviewMapper.re_count(pro_num);
	}
}
