package com.food.basic.payinfo;

import java.util.Date;

//import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//@AllArgsConstructor 빌더 순서 관련 오류날때 사용, 권장x
@Builder
@Data
public class PayInfoVO {
	
	private Integer p_id;
	private Long ord_code;
	private String u_id;
	private String paymethod;
	private int p_price;
	private String p_status;
	private String payinfo;
	private Date p_date;
}
