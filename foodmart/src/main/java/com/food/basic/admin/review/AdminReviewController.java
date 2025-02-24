package com.food.basic.admin.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;
import com.food.basic.review.ReviewVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/review/*")
@Controller
public class AdminReviewController {
	// 리뷰/문의 
	//http://responsive-food.onedaynet.co.kr/totalAdmin/_product_talk.list.php?pt_type=%EC%83%81%ED%92%88%EB%A6%AC%EB%B7%B0&menuUid=285
	
	private final AdminReviewService adminReviewService;
	
	//리뷰목록
	@GetMapping("/re_list/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> re_list(@PathVariable("pro_num")int pro_num, @PathVariable("page")int page) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		
		Map<String, Object> map = new HashMap<>();
		//후기 목록
		Criteria cri = new Criteria();
		cri.setAmount(10);
		cri.setPageNum(page);
		List<ReviewVO> re_list = adminReviewService.re_list(pro_num, cri);
		
		//페이징 정보
		int re_count = adminReviewService.re_count(pro_num);
		PageDTO pageMaker = new PageDTO(cri, re_count);
		
		map.put("re_list", re_list);
		map.put("pageMaker", pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
				
		return entity;
		
	}
	
	//리뷰삭제
	@DeleteMapping("/review_delete/{re_code}")
	public ResponseEntity<String> review_delete(@PathVariable("re_code") Long re_code) throws Exception {
		
		log.info("장바구니코드 : " + re_code);
		ResponseEntity<String> entity = null;
		adminReviewService.review_delete(re_code);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	//리뷰상세폼
	@GetMapping("/review_detail/{re_code}")
	public ResponseEntity<ReviewVO> review_detail(@PathVariable("re_code") Long re_code) throws Exception {
		
		log.info("장바구니코드 : " + re_code);
		ResponseEntity<ReviewVO> entity = null;
		//ReviewVO vo = 
		adminReviewService.review_detail(re_code);
		//vo.setRe_up_folder(vo.getRe_up_folder().replace("\\", "/"));
		
		entity = new ResponseEntity<ReviewVO>(adminReviewService.review_detail(re_code), HttpStatus.OK);
		
		return entity;
	}
}
