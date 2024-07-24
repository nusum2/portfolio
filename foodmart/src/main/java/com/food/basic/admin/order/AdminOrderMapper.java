package com.food.basic.admin.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.common.dto.Criteria;
import com.food.basic.order.OrderVO;

public interface AdminOrderMapper {
	
	List<OrderVO> order_list(@Param("cri")Criteria cri, @Param("start_date")String start_date, @Param("end_date")String end_date);
	
	int getTotalCount(@Param("cri")Criteria cri, @Param("start_date")String start_date, @Param("end_date")String end_date);
	
	//주문자정보
	OrderVO order_info(Long ord_code);
	
	//주문상품정보
	List<OrderDetailInfoVO> order_detail_info(Long ord_code);
	
	//주문상품 개별삭제
	void order_product_delete(@Param("ord_code")Long ord_code, @Param("pro_num")int pro_num);
	
	//주문기본정보 수정
	void order_basic_update(OrderVO vo);
	
	//주문테이블 총금액변경
	void order_tot_price_change(Long ord_code);
}
