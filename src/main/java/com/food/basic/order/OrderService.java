package com.food.basic.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.basic.cart.CartMapper;
import com.food.basic.common.dto.Criteria;
import com.food.basic.payinfo.PayInfoMapper;
import com.food.basic.payinfo.PayInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
	
	private final OrderMapper orderMapper;
	private final PayInfoMapper payInfoMapper;
	private final CartMapper cartMapper;
	
	@Transactional
	public void order_process(OrderVO vo, String u_id, String paymethod, String p_status, String payinfo) {
		
		//주문테이블 insert
		vo.setU_id(u_id);
		orderMapper.order_insert(vo);
		//주문상세테이블 insert
		orderMapper.orderDetail_insert(vo.getOrd_code(), u_id);
		//결제테이블 insert
		PayInfoVO p_vo = PayInfoVO.builder()
				.ord_code(vo.getOrd_code())
				.u_id(u_id)
				.paymethod(paymethod)
				.p_disprice(vo.getOrd_price())
				.p_status(p_status)
				.payinfo(payinfo)
				.build();
		
		payInfoMapper.payInfo_insert(p_vo);
		//장바구니테이블 delete
		cartMapper.cart_empty(u_id);
		
		
	}
	
	@Transactional
	public void order_process_kakao(OrderVO vo, String kakao_id, String paymethod, String p_status, String payinfo) {
		
		//주문테이블 insert
		vo.setU_id(kakao_id);
		orderMapper.order_insert(vo);
		//주문상세테이블 insert
		orderMapper.orderDetail_insert_kakao(vo.getOrd_code(), kakao_id);
		//결제테이블 insert
		PayInfoVO p_vo = PayInfoVO.builder()
				.ord_code(vo.getOrd_code())
				.u_id(kakao_id)
				.paymethod(paymethod)
				.p_disprice(vo.getOrd_price())
				.p_status(p_status)
				.payinfo(payinfo)
				.build();
		
		payInfoMapper.payInfo_insert_kakao(p_vo);
		//장바구니테이블 delete
		cartMapper.cart_empty(kakao_id);
		
	}
	
	@Transactional
	public void order_process_naver(OrderVO vo, String naver_id, String paymethod, String p_status, String payinfo) {
		
		//주문테이블 insert
		vo.setU_id(naver_id);
		orderMapper.order_insert(vo);
		//주문상세테이블 insert
		orderMapper.orderDetail_insert_naver(vo.getOrd_code(), naver_id);
		//결제테이블 insert
		PayInfoVO p_vo = PayInfoVO.builder()
				.ord_code(vo.getOrd_code())
				.u_id(naver_id)
				.paymethod(paymethod)
				.p_disprice(vo.getOrd_price())
				.p_status(p_status)
				.payinfo(payinfo)
				.build();
		
		payInfoMapper.payInfo_insert_naver(p_vo);
		//장바구니테이블 delete
		cartMapper.cart_empty(naver_id);
		
	}
	
	public List<OrderHistoryVO> order_history(String u_id) {
		
		return orderMapper.order_history(u_id);
	}
	
	public List<OrderHistoryVO> order_history_kakao(String kakao_id) {
		
		return orderMapper.order_history(kakao_id);
	}

	public List<OrderHistoryVO> order_history_naver(String naver_id) {
	
		return orderMapper.order_history(naver_id);
	}
	
	public OrderHistoryVO order_info(Long ord_code) {
		
		return orderMapper.order_info(ord_code);
	}
	
	public List<OrderHistoryVO> order_history_detail(Long ord_code) {
		
		return orderMapper.order_history_detail(ord_code);
	}
	
	public int getTotalCount(Criteria cri) {
		
		return orderMapper.getTotalCount(cri);
	}
}
