package com.food.basic.admin.product;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ProductDTO {
	private Integer pro_num;
	private int pro_price;
	private int pro_discount;
	private int pro_disprice;
	private String pro_buy;
}
