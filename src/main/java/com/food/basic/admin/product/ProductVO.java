package com.food.basic.admin.product;

import java.util.Date;

import lombok.Data;

/*
        pro_num,             
        cate_code,          
        pro_name,             
        pro_price,            
        pro_discount,         
        pro_publisher,       
        pro_content,        
        pro_up_folder,        
        pro_img,              
        pro_amount,         
        pro_buy,            
        pro_date,             
        pro_updatedate       
*/

@Data
public class ProductVO {
	
	private Integer pro_num;
	private Integer cate_code; //2차 카테고리 코드
	private String pro_name;
	private int pro_price;
	private int pro_discount;
	private int pro_disprice;
	private String pro_publisher;
	private String pro_content;
	private String pro_up_folder;
	private String pro_img;
	private int pro_amount;
	private String pro_buy;
	private Date pro_date;
	private Date pro_updatedate;
	private String pro_publtell;
	private String pro_publemail;
}
