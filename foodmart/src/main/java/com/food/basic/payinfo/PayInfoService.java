package com.food.basic.payinfo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@Service
public class PayInfoService {
	
	private final PayInfoMapper payInfoMapper;
	
	public PayInfoVO ord_pay_info(Long ord_code) {
		
		return payInfoMapper.ord_pay_info(ord_code);
		
	}
}
