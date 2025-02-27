package com.food.basic.admin.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin/category/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminCategoryController {
	
	private final AdminCategoryService adminCategoryService;
	
	@GetMapping("/cate_list")
	public void catelist(Model model) throws Exception{
		
		List<AdminCategoryVO> list = adminCategoryService.getFirstCategoryList();
		
		model.addAttribute("catelist", list);
		
		//글로벌 컨트롤러에서 모델 작업을 해서 아래 코드는 필요없음.
		model.addAttribute("catelist", adminCategoryService.getFirstCategoryList());
		
		log.info("catelist : " + adminCategoryService.getFirstCategoryList());
		
	}
	@GetMapping("/secondcategory/{cate_pcode}")
	public ResponseEntity<List<AdminCategoryVO>> getSecondCategoryList(@PathVariable("cate_pcode") int cate_pcode) throws Exception {
		ResponseEntity<List<AdminCategoryVO>> entity = null;
		
		log.info("1차 카테고리코드 : " + cate_pcode);
		entity = new ResponseEntity<List<AdminCategoryVO>>(adminCategoryService.getSecondCategoryList(cate_pcode), HttpStatus.OK);
		
		return entity;
	}
}
