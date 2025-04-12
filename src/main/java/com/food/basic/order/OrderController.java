package com.food.basic.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
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
import com.food.basic.common.util.FileManagerUtils;
import com.food.basic.kakaologin.KakaoUserInfo;
import com.food.basic.naverlogin.NaverResponse;
import com.food.basic.naverlogin.Response;
import com.food.basic.payinfo.PayInfoService;
import com.food.basic.payinfo.PayInfoVO;
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
	private final PayInfoService payInfoService;
	
	//1.pro_list.html 구매하기 2.pro_detail.html 구매하기 3.장바구니 구매하기
	//1,2번은 cartvo 사용 3번은 미사용, 사용시 에러 발생
	
	//상품이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	//주문정보
	@GetMapping("/orderinfo")
	public String orderinfo(@RequestParam(value = "type", defaultValue = "direct") String type, CartVO vo, Model model, HttpSession session) throws Exception {
		
		if(!type.equals("cartorder")) {
			//장바구니 저장
			cartService.cart_add(vo);
		}
		//일반회원 로그인시
		if(session.getAttribute("login_status") != null) {
		//로그인
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		vo.setU_id(u_id);
		//주문내역
		List<CartProductVO> cart_list = cartService.cart_list(u_id);
		//장바구니 주문가격 총합계
		int total_price = 0;
		cart_list.forEach(d_vo -> {
			d_vo.setPro_up_folder(d_vo.getPro_up_folder().replace("\\", "/"));
			//total_price += (d_vo.getCart_amount() * d_vo.getPro_price());
		});
		for(int i=0; i<cart_list.size(); i++) {
			total_price += (cart_list.get(i).getPro_disprice() * cart_list.get(i).getCart_amount());
		}
		
		model.addAttribute("cart_list", cart_list);
		model.addAttribute("total_price", total_price);
		
		//카카오 로그인시
		}else if(session.getAttribute("kakao_status") != null) {
			//카카오 로그인
			String kakao_id = ((KakaoUserInfo) session.getAttribute("kakao_status")).getNickname();
			vo.setKakao_id(kakao_id);
			//주문내역
			List<CartProductVO> cart_list = cartService.cart_list_kakao(kakao_id);
			//장바구니 주문가격 총합계
			int total_price = 0;
			cart_list.forEach(d_vo -> {
				d_vo.setPro_up_folder(d_vo.getPro_up_folder().replace("\\", "/"));
				//total_price += (d_vo.getCart_amount() * d_vo.getPro_price());
			});
			for(int i=0; i<cart_list.size(); i++) {
				total_price += (cart_list.get(i).getPro_disprice() * cart_list.get(i).getCart_amount());
			}
			
			model.addAttribute("cart_list", cart_list);
			model.addAttribute("total_price", total_price);
		
		//네이버 로그인시
		}else if(session.getAttribute("naver_status") != null) {
			//네이버 로그인
			String naver_id = ((Response) session.getAttribute("naver_status")).getName();
			vo.setNaver_id(naver_id);
			//주문내역
			List<CartProductVO> cart_list = cartService.cart_list_naver(naver_id);
			//장바구니 주문가격 총합계
			int total_price = 0;
			cart_list.forEach(d_vo -> {
				d_vo.setPro_up_folder(d_vo.getPro_up_folder().replace("\\", "/"));
				//total_price += (d_vo.getCart_amount() * d_vo.getPro_price());
			});
			for(int i=0; i<cart_list.size(); i++) {
				total_price += (cart_list.get(i).getPro_disprice() * cart_list.get(i).getCart_amount());
			}
			
			model.addAttribute("cart_list", cart_list);
			model.addAttribute("total_price", total_price);
		}
			
		return "order/orderinfo";
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

		//일반회원 로그인시
		if(session.getAttribute("login_status") != null) {
			String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
			vo.setU_id(u_id);
			//결제정보
			String payinfo = pay_nobank + "/" + pay_nobank_user;
			orderService.order_process(vo, u_id, "무통장입금", "미납", payinfo);
		//카카오 로그인시	
		}else if(session.getAttribute("kakao_status") != null) {
			String kakao_id = ((KakaoUserInfo) session.getAttribute("kakao_status")).getNickname();
			vo.setKakao_id(kakao_id);
			//결제정보
			String payinfo = pay_nobank + "/" + pay_nobank_user;
			orderService.order_process_kakao(vo, kakao_id, "무통장입금", "미납", payinfo);
		//네이버 로그인시
		}else if(session.getAttribute("naver_status") != null) {
			String naver_id = ((Response) session.getAttribute("naver_status")).getName();
			vo.setNaver_id(naver_id);
			//결제정보
			String payinfo = pay_nobank + "/" + pay_nobank_user;
			orderService.order_process_naver(vo, naver_id, "무통장입금", "미납", payinfo);
		}
		
		
		return "redirect:/order/ordercomplete";
	}
	
	//주문완료
	@GetMapping("/ordercomplete")
	public void ordercomplete() throws Exception {
		
	}
	
	//주문내역
	@GetMapping("/order_history")
	public void order_history(HttpSession session, Model model) throws Exception {
		
		//일반회원 로그인시
		if(session.getAttribute("login_status") != null) {
			String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
			List<OrderHistoryVO> order_history = orderService.order_history(u_id);
			order_history.forEach(vo -> vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/")));
			//페이징
			model.addAttribute("order_history", order_history);
			
			log.info("리스트 : " + order_history);
		//카카오 로그인시
		}else if(session.getAttribute("kakao_status") != null) { 
			String kakao_id = ((KakaoUserInfo) session.getAttribute("kakao_status")).getNickname();
			List<OrderHistoryVO> order_history = orderService.order_history_kakao(kakao_id);
			order_history.forEach(vo -> vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/")));
			//페이징
			model.addAttribute("order_history", order_history);
			
			log.info("리스트 : " + order_history);
		//네이버 로그인시	
		}else if(session.getAttribute("naver_status") != null) {
			String naver_id = ((NaverResponse) session.getAttribute("naver_status")).getResponse().getName();
			List<OrderHistoryVO> order_history = orderService.order_history_naver(naver_id);
			order_history.forEach(vo -> vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/")));
			//페이징
			model.addAttribute("order_history", order_history);
			
			log.info("리스트 : " + order_history);
			
		}
				
	}
	
	//주문내역상세
	@GetMapping("/order_history_detail")
	public ResponseEntity<Map<String, Object>> order_history_detail(Long ord_code) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		
		Map<String, Object> map = new HashMap<>();
		
		//주문자정보
		OrderHistoryVO vo = orderService.order_info(ord_code);
		map.put("ord_basic", vo);
		
		//주문상품정보
		List<OrderHistoryVO> ord_product_list = orderService.order_history_detail(ord_code);
		
		// 클라이언트에 \를 /로 변환하여, model작업전에 처리함.  2024\07\01 -> 2024/07/01
		ord_product_list.forEach(ord_pro -> {
			ord_pro.setPro_up_folder(ord_pro.getPro_up_folder().replace("\\", "/"));
		});
		
		map.put("ord_pro_list", ord_product_list);
		
		//결제정보
		PayInfoVO p_vo = payInfoService.ord_pay_info(ord_code);
		map.put("payinfo", p_vo);
				
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		
		return entity;
	}
	
	//주문내역상세에서 주문상품 이미지보여주기. 1)<img src="매핑주소"> 2) <img src="test.gif">
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
	
	return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
}
