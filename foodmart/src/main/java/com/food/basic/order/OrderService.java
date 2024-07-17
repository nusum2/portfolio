package com.food.basic.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.basic.cart.CartMapper;
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
	public void order_process(OrderVO vo, String u_id) {
		
		//주문테이블 insert
		vo.setU_id(u_id);
		orderMapper.order_insert(vo);
		//주문상세테이블 insert
		orderMapper.orderDetail_insert(vo.getOrd_code(), u_id);
		//결제테이블 insert
		PayInfoVO p_vo = PayInfoVO.builder()
				.ord_code(vo.getOrd_code())
				.p_price(vo.getOrd_price())
				.paymethod("kakaopay")
				.p_status("완납")
				.build();
		
		payInfoMapper.payInfo_insert(p_vo);
		//장바구니테이블 delete
		cartMapper.cart_empty(u_id);
	}
}
