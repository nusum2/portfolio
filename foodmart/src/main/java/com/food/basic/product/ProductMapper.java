package com.food.basic.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.admin.product.ProductVO;
import com.food.basic.common.dto.Criteria;

public interface ProductMapper {
	
	List<ProductVO> pro_list(@Param("cate_code") int cate_code, @Param("cri") Criteria cri);
	
	int getCountProductByCategory(int cate_code);
	
	ProductVO pro_info(int pro_num);
}
