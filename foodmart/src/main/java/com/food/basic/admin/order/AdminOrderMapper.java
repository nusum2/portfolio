package com.food.basic.admin.order;

import java.util.List;

import com.food.basic.common.dto.Criteria;
import com.food.basic.order.OrderVO;

public interface AdminOrderMapper {
	
	List<OrderVO> order_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
	
	OrderVO order_info(Long ord_code);
	
	List<OrderDetailInfoVO> order_detail_info(Long ord_code);
}
