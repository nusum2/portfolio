package com.food.basic.admin.analysis;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin/analysis/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class AnalysisController {
	// 차트
	// http://responsive-food.onedaynet.co.kr/totalAdmin/_cntlog.php?menuUid=184 참고
	
	private final AnalysisService analysisService;
	
	@GetMapping("/orderStats")
	public void getOrderStatus(Model model) {
		
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		
		model.addAttribute("year", year); //현재 년도
		model.addAttribute("month", month); //현재 달
	}
	
	//1차 카테고리별 월별 매출현황
	@GetMapping("/monthlySalesStatusByTopCategory")
	@ResponseBody
	public List<Map<String, Object>> getMonthlySalesStatusByTopCategory(int year, int month) {
		
		String ord_date = String.format("%s/%s", year, (month < 10 ? "0" + String.valueOf(month) : month));
		log.info("선택일 : " + ord_date);
		
		List<Map<String, Object>> listObjMap = analysisService.monthlySalesStatusByTopCategory(ord_date);
		
		return listObjMap;
	}
}
