package com.food.basic.naverlogin;

import lombok.Data;

@Data
public class NaverResponse {
	
	private String resultcode;
	private String message;
	
	Response response;
}
