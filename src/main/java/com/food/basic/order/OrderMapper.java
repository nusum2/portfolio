package com.food.basic.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.common.dto.Criteria;

public interface OrderMapper {
	
	void order_insert(OrderVO vo);
	
	//장바구니 테이블을 기반으로 주문상세 테이블에 데이터를 저장 https://velog.io/@ryuneng2/Spring-selectKey-%EC%8B%9C%ED%80%80%EC%8A%A4%ED%9A%8D%EB%93%9D
	void orderDetail_insert(@Param("ord_code") Long ord_code, @Param("u_id") String u_id);
	
	//주문내역
	List<OrderHistoryVO> order_history(String u_id);
	
	//주문자정보
	OrderHistoryVO order_info(Long ord_code);
	
	//주문내역상세
	List<OrderHistoryVO> order_history_detail(Long ord_code);
	
	//카운트
	int getTotalCount(@Param("cri")Criteria cri);
}
