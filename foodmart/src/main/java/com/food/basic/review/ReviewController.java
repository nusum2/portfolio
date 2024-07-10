package com.food.basic.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/review/*")
@RequiredArgsConstructor
@Slf4j
@RestController
public class ReviewController {
	
	private final ReviewService reviewService;
	
	//리뷰목록
	@GetMapping("/re_list/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> re_list(@PathVariable("pro_num")int pro_num, @PathVariable("page")int page) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		
		Map<String, Object> map = new HashMap<>();
		//후기 목록
		Criteria cri = new Criteria();
		cri.setAmount(2);
		cri.setPageNum(page);
		List<ReviewVO> re_list = reviewService.re_list(pro_num, cri);
		
		//페이징 정보
		int re_count = reviewService.re_count(pro_num);
		PageDTO pageMaker = new PageDTO(cri, re_count);
		
		map.put("re_list", re_list);
		map.put("pageMaker", pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
				
		return entity;
		
	}
}
