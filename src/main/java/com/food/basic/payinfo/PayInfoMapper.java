package com.food.basic.payinfo;

public interface PayInfoMapper {
	
	void payInfo_insert(PayInfoVO vo);
	
	PayInfoVO ord_pay_info(Long ord_code);
	
	//결제테이블 총금액변경
	void pay_tot_price_change(Long ord_code);
}
