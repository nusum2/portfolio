package com.food.basic.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.admin.product.ProductVO;
import com.food.basic.common.dto.Criteria;

public interface ProductMapper {
	
	//상품 목록
	List<ProductVO> pro_list(@Param("cate_code") int cate_code, @Param("cri") Criteria cri);
	//카운트+카테고리
	int getCountProductByCategory(int cate_code);
	//상품정보
	ProductVO pro_info(int pro_num);
	//상품 상세정보
	ProductVO pro_detail(int pro_num);
}
