package com.food.basic.naverlogin;

import lombok.Data;

@Data
public class NaverCallback {
	
	private String code;
	private String state;
	private String error;
	private String error_description;
}
