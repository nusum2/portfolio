package com.food.basic.cart;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {
	
	private final CartMapper cartMapper;
	
	public void cart_add(CartVO vo) {
		cartMapper.cart_add(vo);
	}
	
	public void cart_del(Long cart_code) {
		cartMapper.cart_del(cart_code);
	}
	
	public void cart_change(@Param("cart_code") Long cart_code,@Param("cart_amount") int cart_amount) {
		cartMapper.cart_change(cart_code, cart_amount);
	}
	
	public List<CartProductVO> cart_list(String u_id) {
		return cartMapper.cart_list(u_id);
	}
	
	public List<CartProductVO> cart_list_kakao(String kakao_id) {
		return cartMapper.cart_list_kakao(kakao_id);
	}
	
	public List<CartProductVO> cart_list_naver(String naver_id) {
		return cartMapper.cart_list_naver(naver_id);
	}
	
	public void cart_empty(String u_id) {
		cartMapper.cart_empty(u_id);
	}
	
	public void cart_empty_kakao(String kakao_id) {
		cartMapper.cart_empty_kakao(kakao_id);
	}
	
	public void cart_empty_naver(String naver_id) {
		cartMapper.cart_empty_naver(naver_id); 
	}
}
