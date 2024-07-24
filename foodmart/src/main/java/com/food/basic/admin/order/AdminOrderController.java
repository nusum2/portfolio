package com.food.basic.admin.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.food.basic.common.constants.Constants;
import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;
import com.food.basic.common.util.FileManagerUtils;
import com.food.basic.order.OrderVO;
import com.food.basic.payinfo.PayInfoService;
import com.food.basic.payinfo.PayInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin/order/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminOrderController {
	
	private final AdminOrderService adminOrderService;
	private final PayInfoService payInfoService;
	
	//상품 이미지 업로드
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	//주문내역
	@GetMapping("/order_list")
	public void order_list(@ModelAttribute("start_date") String start_date, 
							@ModelAttribute("end_date") String end_date, Criteria cri, Model model) throws Exception {
		
		cri.setAmount(Constants.ADMIN_ORDER_LIST_AMOUNT);
		
		log.info("Criteria : " + cri);
		
		List<OrderVO> order_list = adminOrderService.order_list(cri, start_date, end_date);
		
		
		int totalCount = adminOrderService.getTotalCount(cri, start_date, end_date);
		
		//페이징
		log.info("카운트:" + new PageDTO(cri, totalCount));
		model.addAttribute("order_list", order_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		log.info("리스트 : " + order_list);
	}
	
	//주문상세정보 http://www.manual.oneware.co.kr/official.php/home/info/2037 참고
	@GetMapping("/order_detail_info")
	public ResponseEntity<Map<String, Object>> order_detail_info(Long ord_code) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		
		Map<String, Object> map = new HashMap<>();
		
		//주문자(수령인)정보
		OrderVO vo = adminOrderService.order_info(ord_code);
		map.put("ord_basic", vo);
		
		//주문상품정보
		List<OrderDetailInfoVO> ord_product_list = adminOrderService.order_detail_info(ord_code);
		
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
	
	// 주문상세정보에서 주문상품 이미지보여주기. 1)<img src="매핑주소"> 2) <img src="test.gif">
	@GetMapping("/image_display")
		public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	//상품주문 개별삭제
	@GetMapping("/order_product_delete")
	public ResponseEntity<String> order_product_delete(Long ord_code, int pro_num) throws Exception {
		ResponseEntity<String> entity = null;
		
		//db연동
		adminOrderService.order_product_delete(ord_code, pro_num);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;
	}
	
	//상품주문 정보수정
	@GetMapping("/order_basic_update")
	public ResponseEntity<String> order_basic_update(OrderVO vo) throws Exception {
		log.info("주문기본정보 : " + vo);
		
		ResponseEntity<String> entity = null;
		
		//db연동
		adminOrderService.order_basic_update(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;
	}
}
