package com.food.basic.admin.product;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ProductDTO {
	private Integer pro_num;
	private int pro_price;
	private String pro_buy;
}
