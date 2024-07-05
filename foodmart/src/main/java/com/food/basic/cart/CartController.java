package com.food.basic.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.food.basic.user.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cart/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class CartController {
	
	private final CartService cartService;
	
	//장바구니 추가
	@GetMapping("/cart_add")
	public ResponseEntity<String> cart_add(CartVO vo, HttpSession session) throws Exception {
		
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		vo.setU_id(u_id);
		
		ResponseEntity<String> entity = null;
		cartService.cart_add(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		
		return entity;
	}
}
