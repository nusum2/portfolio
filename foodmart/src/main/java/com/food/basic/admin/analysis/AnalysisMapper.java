package com.food.basic.admin.analysis;

import java.util.List;
import java.util.Map;

public interface AnalysisMapper {
	
	List<Map<String, Object>> findDailyOrderStats();
	//카테고리별 월매출 + 판매량
	List<Map<String, Object>> monthlySalesStatusByTopCategory(String ord_date);
	//카테고리별 월 판매량
	//List<Map<String, Object>> monthlySalesAmountByTopCategory(String ord_date);
}
