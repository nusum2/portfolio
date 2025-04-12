package com.food.basic.payinfo;

public interface PayInfoMapper {
	
	//결제내용 저장
	void payInfo_insert(PayInfoVO vo);
	//카카오 로그인 결제내용 저장
	void payInfo_insert_kakao(PayInfoVO vo);
	//네이버 로그인 결제내용 저장
	void payInfo_insert_naver(PayInfoVO vo);
	//결제내용
	PayInfoVO ord_pay_info(Long ord_code);	
	//결제테이블 총금액변경
	void pay_tot_price_change(Long ord_code);
}
