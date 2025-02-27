package com.food.basic;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.food.basic.admin.carousel.CarouselService;
import com.food.basic.admin.carousel.CarouselVO;
import com.food.basic.admin.category.AdminCategoryService;
import com.food.basic.admin.category.AdminCategoryVO;
import com.food.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class HomeController {
	
	private final AdminCategoryService adminCategoryService;
	private final CarouselService carouselService;

	@GetMapping("/")
	public String index(Model model, Criteria cri) {
		
		log.info("인덱스 페이지");
		//카테고리
		List<AdminCategoryVO> cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("user_cate_list", cate_list);
		//캐러셀
		List<CarouselVO> carousel_list = carouselService.carousel_list(cri);
		carousel_list.forEach(vo -> {
			vo.setCaro_up_folder(vo.getCaro_up_folder().replace("\\", "/"));
		});
		
		model.addAttribute("carousel_list", carousel_list);
		log.info("캐러셀 리스트 정보 : " + carousel_list);
		return "index";
	}
		
}
