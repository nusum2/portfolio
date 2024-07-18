package com.food.basic.kakaopay;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.food.basic.cart.CartProductVO;
import com.food.basic.cart.CartService;
import com.food.basic.order.OrderService;
import com.food.basic.order.OrderVO;
import com.food.basic.user.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/kakao/*")
@Controller
public class KakaopayController {

	private final KakaopayService kakaopayService;
	private final CartService cartService;
	private final OrderService orderService;
	
	private OrderVO vo;
	private String u_id;
	
	@GetMapping("/kakaoPayRequest")
	public void kakaoPayRequest() {
		
	}
	
	
	@ResponseBody
	@GetMapping(value =  "/kakaoPay")
	public ReadyResponse kakaoPay(OrderVO vo, HttpSession session) {
		
		//1)결제준비요청(ready)
		/*
		String partnerOrderId, String partnerUserId, String itemName, int quantity, 
    		int totalAmount, int taxFreeAmount, int vatAmount
		*/
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		this.u_id = u_id;
		List<CartProductVO> cart_list = cartService.cart_list(u_id);
		
		String itemName = "";
		int quantity = 0;
		int totalAmount = 0;
		int taxFreeAmount = 0;
		int vatAmount = 0;
		
		for(int i=0; i<cart_list.size(); i++) {
			itemName += cart_list.get(i).getPro_name() + "/";
			quantity += cart_list.get(i).getCart_amount();
			totalAmount += cart_list.get(i).getPro_price() * cart_list.get(i).getCart_amount();
		}
		
		String partnerOrderId = u_id;
		String partnerUserId = u_id;
		
		
		ReadyResponse readyResponse = kakaopayService.ready(partnerOrderId, partnerUserId, itemName, quantity, 
				totalAmount, taxFreeAmount, vatAmount);
		
		log.info("응답데이터: " + readyResponse);
		this.vo = vo;
		
		return readyResponse;
	}
	
	//성공
	@GetMapping("/approval")
	public void approval(String pg_token) {
		log.info("pg_token: " + pg_token);
		//2)결제승인요청
		String approveResponse = kakaopayService.approve(pg_token);
		
		//주문정보저장
		
		log.info("최종결과: " + approveResponse);
		//aid값이 존재하면
		
		//트랜잭션으로 처리 : 주문테이블, 주문상세테이블, 결제테이블, 장바구니 비우기
		if(approveResponse.contains("aid")) {
			orderService.order_process(vo, u_id, "kakaopay", "완료", "kakaopay");
		}
		
	}
	
	//취소
	@GetMapping("/cancel")
	public void cancel() {
		
		
	}
	
	//실패
	@GetMapping("/fail")
	public void fail() {
		
		
	}
}
