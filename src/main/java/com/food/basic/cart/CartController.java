package com.food.basic.cart;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.food.basic.common.util.FileManagerUtils;
import com.food.basic.kakaologin.KakaoUserInfo;
import com.food.basic.naverlogin.NaverResponse;
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
	
	//상품이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	//장바구니 추가
	@GetMapping("/cart_add")
	public ResponseEntity<String> cart_add(CartVO vo, HttpSession session) throws Exception {
		
		if(session.getAttribute("login_status") != null) {
			String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
			vo.setU_id(u_id);	
		}else if(session.getAttribute("kakao_status") != null) {
			String kakao_id = ((KakaoUserInfo) session.getAttribute("kakao_status")).getNickname();
			vo.setKakao_id(kakao_id);
		}else if(session.getAttribute("naver_status") != null) {
			String naver_id = ((NaverResponse) session.getAttribute("naver_status")).getResponse().getName();
			vo.setNaver_id(naver_id);
		}
		
		ResponseEntity<String> entity = null;
		cartService.cart_add(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	//장바구니 목록
	@GetMapping("/cart_list")
	public void cart_list(HttpSession session, Model model) throws Exception {
		
		if(session.getAttribute("login_status") != null) {
			String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
			
			List<CartProductVO> cart_list = cartService.cart_list(u_id);
			cart_list.forEach(vo -> vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/")));
			
			model.addAttribute("cart_list", cart_list);
		}else if(session.getAttribute("kakao_status") != null) {
			String kakao_id = ((KakaoUserInfo) session.getAttribute("kakao_status")).getNickname();
			log.info("카카오 아이디 : " + kakao_id);
			
			List<CartProductVO> cart_list = cartService.cart_list_kakao(kakao_id);
			cart_list.forEach(vo -> vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/")));
			
			model.addAttribute("cart_list", cart_list);
		}else if(session.getAttribute("naver_status") != null) {
			String naver_id = ((NaverResponse) session.getAttribute("naver_status")).getResponse().getName();
			log.info("네이버 아이디 : " + naver_id);
			
			List<CartProductVO> cart_list = cartService.cart_list_naver(naver_id);
			cart_list.forEach(vo -> vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/")));
			
			model.addAttribute("cart_list", cart_list);
		}
		
			
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	//장바구니 삭제
	@GetMapping("/cart_del")
	public String cart_del(Long cart_code) throws Exception {
		
		cartService.cart_del(cart_code);
		
		return "redirect:/cart/cart_list";
	}
	
	//장바구니 수량변경
	@GetMapping("/cart_change")
	public String cart_change(@Param("cart_code") Long cart_code,@Param("cart_amount") int cart_amount) throws Exception {
		
		log.info("장바구니 코드 : " + cart_code);
		log.info("장바구니 수량 : " + cart_amount);
		
		cartService.cart_change(cart_code, cart_amount);
		
		return "redirect:/cart/cart_list";
	}
	
	//장바구니 비우기
	@GetMapping("/cart_empty")
	public String cart_empty(HttpSession session) throws Exception {
		
		//일반회원 로그인시
		if(session.getAttribute("login_status") != null) {
			String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
			cartService.cart_empty(u_id);
		//카카오 로그인시	
		}else if(session.getAttribute("kakao_status") != null) {
			String kakao_id = ((KakaoUserInfo) session.getAttribute("kakao_status")).getNickname();
			cartService.cart_empty_kakao(kakao_id);
		//네이버 로그인시	
		}else if(session.getAttribute("naver_status") != null) {
			String naver_id = ((NaverResponse) session.getAttribute("naver_status")).getResponse().getName();
			cartService.cart_empty_naver(naver_id);
		}
		
		return "redirect:/cart/cart_list";
	}
}
