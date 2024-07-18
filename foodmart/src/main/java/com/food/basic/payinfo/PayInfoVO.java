package com.food.basic.payinfo;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PayInfoVO {
	
	private Integer p_id;
	private Long ord_code;
	private String paymethod;
	private int p_price;
	private String p_status;
	private Date p_date;
	private String payinfo;
}
