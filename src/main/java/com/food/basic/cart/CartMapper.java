package com.food.basic.cart;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CartMapper {
	
	void cart_add(CartVO vo);
	
	List<CartProductVO> cart_list(String u_id);
	
	List<CartProductVO> cart_list_kakao(String kakao_id);
	
	List<CartProductVO> cart_list_naver(String naver_id);
	
	void cart_del(Long cart_code);
	
	void cart_change(@Param("cart_code") Long cart_code,@Param("cart_amount") int cart_amount);
	
	void cart_empty(String u_id);
	
	void cart_empty_kakao(String kakao_id);
	
	void cart_empty_naver(String naver_id);
}
