package com.food.basic;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.food.basic.admin.category.AdminCategoryService;
import com.food.basic.admin.category.AdminCategoryVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class HomeController {
	
	private final AdminCategoryService adminCategoryService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		log.info("인덱스 페이지");
		
		List<AdminCategoryVO> cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("user_cate_list", cate_list);
		
		return "index";
	}
		
}
