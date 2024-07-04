package com.food.basic.cart;

import java.util.Date;

import lombok.Data;

//cart_code, pro_num, u_id, cart_amount, cart_date

@Data
public class CartVO {
	
	private Long cart_code;
	private int pro_num;
	private String u_id;
	private int cart_amount;
	private Date cart_date; //Carlendar, LocalDate, LocalTime, LocalDateTime
}
