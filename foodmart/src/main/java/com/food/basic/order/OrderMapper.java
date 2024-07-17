package com.food.basic.order;

import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
	
	void order_insert(OrderVO vo);
	
	//장바구니 테이블을 기반으로 주문상세 테이블에 데이터를 저장
	void orderDetail_insert(@Param("ord_code") Long ord_code,@Param("u_id") String u_id);
}
