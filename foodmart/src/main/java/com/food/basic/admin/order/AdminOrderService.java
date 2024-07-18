package com.food.basic.admin.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;
import com.food.basic.order.OrderVO;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AdminOrderService {
	
	private final AdminOrderMapper adminOrderMapper;
	
	public List<OrderVO> order_list(Criteria cri) {
		
		return adminOrderMapper.order_list(cri);
	}
	
	public int getTotalCount(Criteria cri) {
		
		return adminOrderMapper.getTotalCount(cri);
	}
	
	public OrderVO order_info(Long ord_code) {
		
		return adminOrderMapper.order_info(ord_code);
	}
}
