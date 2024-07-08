package com.food.basic.review;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/review/*")
@RequiredArgsConstructor
@Slf4j
@RestController
public class ReviewController {
	
	private final ReviewService reviewService;
	
	
}
