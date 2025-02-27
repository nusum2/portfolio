package com.food.basic.payinfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/payinfo/*")
@RequiredArgsConstructor
@Controller
public class PayInfoController {
 
	private final PayInfoService payInfoService;
}
