package com.food.basic.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminCategoryService {
	
	private final AdminCategoryMapper adminCategoryMapper;
	
	public List<AdminCategoryVO> getFirstCategoryList() {
		
		return adminCategoryMapper.getFirstCategoryList();
	}

	public List<AdminCategoryVO> getSecondCategoryList(int cate_pcode) {
		
		return adminCategoryMapper.getSecondCategoryList(cate_pcode);
	}
	
	public AdminCategoryVO getFirstCategoryBySecondCategory(int cate_code) {
		
		return adminCategoryMapper.getFirstCategoryBySecondCategory(cate_code);
	}
}
