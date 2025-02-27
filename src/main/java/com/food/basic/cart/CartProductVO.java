package com.food.basic.cart;

import lombok.Data;

@Data
public class CartProductVO {
	
	private Long cart_code;
	private int pro_num;
	private String pro_name;
	private String pro_up_folder;
	private String pro_img;
	private int pro_disprice;
	private int cart_amount;
}
