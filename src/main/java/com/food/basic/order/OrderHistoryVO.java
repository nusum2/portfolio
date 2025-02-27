package com.food.basic.order;

import java.util.Date;

import lombok.Data;

@Data
public class OrderHistoryVO {
	
	private Long ord_code;
	private String u_id;
	private String ord_name;
	private String ord_zip_code;
	private String ord_addr;
	private String ord_deaddr;
	private String ord_phone;
	private int ord_price;
	private String ord_desc;
	private String ord_admin_memo; //관리자메모
	private Date ord_regdate;
	private int pro_num;
	private int dt_amount;
	private int dt_price;
	private String pro_name;
	private String pro_up_folder;
	private String pro_img;
	private int pro_disprice;
}
