package com.food.basic.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.admin.product.ProductVO;
import com.food.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {
	
	private final ProductMapper productMapper;
	
	public List<ProductVO> pro_list(int cate_code, Criteria cri) {
		
		return productMapper.pro_list(cate_code, cri);
	}
	
	public int getCountProductByCategory(int cate_code) {
		
		return productMapper.getCountProductByCategory(cate_code);
	}
	
	public ProductVO pro_info(int pro_num) {
		
		return productMapper.pro_info(pro_num);
	}
	
}
