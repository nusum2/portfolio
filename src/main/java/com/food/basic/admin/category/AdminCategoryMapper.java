package com.food.basic.admin.category;

import java.util.List;

public interface AdminCategoryMapper {
	
	List<AdminCategoryVO> getFirstCategoryList();
	
	List<AdminCategoryVO> getSecondCategoryList(int cate_pcode);
	
	AdminCategoryVO getFirstCategoryBySecondCategory(int cate_code);
}
