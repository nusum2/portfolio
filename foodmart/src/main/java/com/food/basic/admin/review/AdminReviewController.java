package com.food.basic.admin.review;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
