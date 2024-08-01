package com.food.basic.admin.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.basic.common.dto.Criteria;
import com.food.basic.order.OrderVO;
import com.food.basic.payinfo.PayInfoMapper;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AdminOrderService {
	
	private final AdminOrderMapper adminOrderMapper;
	private final PayInfoMapper payInfoMapper;
	
	public List<OrderVO> order_list(Criteria cri, String start_date, String end_date) {
		
		return adminOrderMapper.order_list(cri, start_date, end_date);
	}
	
	public List<Map<String, Object>> order_list2() {
		
		return adminOrderMapper.order_list2();
	}
	
	public int getTotalCount(Criteria cri, String start_date, String end_date) {
		
		return adminOrderMapper.getTotalCount(cri, start_date, end_date);
	}
	
	public OrderVO order_info(Long ord_code) {
		
		return adminOrderMapper.order_info(ord_code);
	}
	
	public List<OrderDetailInfoVO> order_detail_info(Long ord_code) {
		
		return adminOrderMapper.order_detail_info(ord_code);
	}
	@Transactional
	public void order_product_delete(Long ord_code, int pro_num) {
		//주문상품 개별삭제
		adminOrderMapper.order_product_delete(ord_code, pro_num);
		//주문테이블 금액변경
		adminOrderMapper.order_tot_price_change(ord_code);
		//결제테이블 금액변경
		payInfoMapper.pay_tot_price_change(ord_code);
	}
	
	public void order_basic_update(OrderVO vo) {
		
		adminOrderMapper.order_basic_update(vo);
	}
}
