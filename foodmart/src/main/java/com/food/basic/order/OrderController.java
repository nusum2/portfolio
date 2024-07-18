package com.food.basic.order;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.food.basic.cart.CartProductVO;
import com.food.basic.cart.CartService;
import com.food.basic.cart.CartVO;
import com.food.basic.user.UserService;
import com.food.basic.user.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/order/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class OrderController {
	
	private final OrderService orderService;
	private final CartService cartService;
	private final UserService userService;
	
	//1.pro_list.html 구매하기 2.pro_detail.html 구매하기 3.장바구니 구매하기
	//1,2번은 cartvo 사용 3번은 미사용, 사용시 에러 발생
	//주문정보
	@GetMapping("/orderinfo")
	public String orderinfo(@RequestParam(value = "type", defaultValue = "direct") String type, CartVO vo, Model model, HttpSession session) throws Exception {
		
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		vo.setU_id(u_id);
		
		if(!type.equals("cartorder")) {
			//장바구니 저장
			cartService.cart_add(vo);
		}
		
		
		//주문내역
		List<CartProductVO> cart_list = cartService.cart_list(u_id);
		//장바구니 주문가격 총합계
		int total_price = 0;
		cart_list.forEach(d_vo -> {
			d_vo.setPro_up_folder(d_vo.getPro_up_folder().replace("\\", "/"));
			//total_price += (d_vo.getCart_amount() * d_vo.getPro_price());
		});
		for(int i=0; i<cart_list.size(); i++) {
			total_price += (cart_list.get(i).getPro_price() * cart_list.get(i).getCart_amount());
		}
		model.addAttribute("cart_list", cart_list);
		model.addAttribute("total_price", total_price);
		
		return "/order/orderinfo";
	}
	
	//주문자와 동일
	@GetMapping("/ordersame")
	public ResponseEntity<UserVO> ordersame(HttpSession session) throws Exception {
		ResponseEntity<UserVO> entity = null;
		
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		
		//회원정보 받아오기
		entity = new ResponseEntity<UserVO>(userService.login(u_id), HttpStatus.OK);
		
		
		return entity;
	}
	
	//무통장입금
	@PostMapping("/ordersave")
	public String ordersave(OrderVO vo, String pay_nobank, String pay_nobank_user, HttpSession session) throws Exception {
		
		//무통장 입금 로그
		log.info("주문정보: " + vo);
		log.info("입금은행: " + pay_nobank);
		log.info(("예금주:" + pay_nobank_user));
		
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		vo.setU_id(u_id);
		//결제정보
		String payinfo = pay_nobank + "/" + pay_nobank_user;
		orderService.order_process(vo, u_id, "무통장입금", "미납", payinfo);
		
		return "redirect:/order/ordercomplete";
	}
	
	//주문완료
	@GetMapping("/ordercomplete")
	public void ordercomplete() throws Exception {
		
	}
	
}
