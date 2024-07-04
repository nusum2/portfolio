package com.food.basic.product;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.food.basic.admin.category.AdminCategoryService;
import com.food.basic.admin.category.AdminCategoryVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice(basePackages = {"com.food.basic.product"}) //카테고리가 사용되는 컨트롤러의 패키지를 설정
public class GlobalControllerAdvice {
	
	private final AdminCategoryService adminCategoryService;
	
	@ModelAttribute
	public void comm_test(Model model) {
		log.info("공통코드 실행");
		
		List<AdminCategoryVO> cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("user_cate_list", cate_list);
	}
}
