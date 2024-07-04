package com.food.basic.admin.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.common.dto.Criteria;

public interface AdminProductMapper {
	
	void pro_insert(ProductVO vo);
	
	List<ProductVO> pro_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
	
	ProductVO pro_edit(Integer pro_num);
	
	void pro_edit_ok(ProductVO vo);
	
	void pro_delete(Integer pro_num);
	
	void pro_checked_modify1(@Param("pro_num") Integer pro_num, @Param("pro_price") Integer pro_price, @Param("pro_buy") String pro_buy);
	
	void pro_checked_modify2(List<ProductDTO> pro_modify_list);
}
