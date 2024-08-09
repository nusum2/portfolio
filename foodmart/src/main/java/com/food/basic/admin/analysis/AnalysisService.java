package com.food.basic.admin.analysis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AnalysisService {
	
	private final AnalysisMapper analysisMapper;
	
	public List<Map<String, Object>> getDailyOrderStats() {
		
		return analysisMapper.findDailyOrderStats();
	}
	
	public List<Map<String, Object>> monthlySalesStatusByTopCategory(String ord_date) {
		
		return analysisMapper.monthlySalesStatusByTopCategory(ord_date);
	}
	
//	public List<Map<String, Object>> monthlySalesAmountByTopCategory(String ord_date) {
//		
//		return analysisMapper.monthlySalesAmountByTopCategory(ord_date);
//	}
}
