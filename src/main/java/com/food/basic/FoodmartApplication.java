package com.food.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@MapperScan(basePackages = "com.food.basic.**")
@SpringBootApplication(exclude = SecurityAutoConfiguration.class) //스프링 시큐리티 적용 안됨
public class FoodmartApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodmartApplication.class, args);
	}

}
