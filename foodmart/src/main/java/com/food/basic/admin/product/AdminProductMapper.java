package com.food.basic.admin.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.common.dto.Criteria;

public interface AdminProductMapper {
	//등록
	void pro_insert(ProductVO vo);
	//목록
	List<ProductVO> pro_list(Criteria cri);
	//총개수
	int getTotalCount(Criteria cri);
	//수정폼
	ProductVO pro_edit(Integer pro_num);
	//수정
	void pro_edit_ok(ProductVO vo);
	//삭제
	void pro_delete(Integer pro_num);
	//체크수정1
	void pro_checked_modify1(@Param("pro_num") Integer pro_num, @Param("pro_price") Integer pro_price, @Param("pro_discount") Integer pro_discount, @Param("pro_disprice") Integer pro_disprice, @Param("pro_buy") String pro_buy);
	//체크수정2
	void pro_checked_modify2(List<ProductDTO> pro_modify_list);
}
