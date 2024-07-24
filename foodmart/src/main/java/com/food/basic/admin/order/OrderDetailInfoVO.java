package com.food.basic.admin.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailInfoVO {
	
	//ot.ord_code, ot.pro_num, ot.dt_amount, ot.dt_price, p.pro_name, p.pro_up_folder, p.pro_img
	
	private Long ord_code;
	private Integer pro_num;
	private int dt_amount;
	private int dt_price;
	private String pro_name;
	private String pro_up_folder;
	private String pro_img;
}
