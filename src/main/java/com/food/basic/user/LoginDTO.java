package com.food.basic.user;

import java.util.Date;

import lombok.Data;

@Data
public class LoginDTO {
	
	private String u_id;
	private String u_password;
	private Date u_lastlogin;
}
