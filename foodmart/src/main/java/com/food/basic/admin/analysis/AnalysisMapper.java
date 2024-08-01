package com.food.basic.admin.analysis;

import java.util.List;
import java.util.Map;

public interface AnalysisMapper {
	
	List<Map<String, Object>> findDailyOrderStats();
	
	List<Map<String, Object>> monthlySalesStatusByTopCategory(String ord_date);
}
