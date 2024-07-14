package com.food.basic.order;

import lombok.Data;

@Data
public class OrderDetailVO {
	
	private Long ord_code;
	private int pro_num;
	private int dt_amount;
	private int dt_price;
}
