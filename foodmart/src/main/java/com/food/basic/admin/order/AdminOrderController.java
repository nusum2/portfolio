package com.food.basic.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;
import com.food.basic.order.OrderVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin/order/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminOrderController {
	
	private final AdminOrderService adminOrderService;
	
	//상품 이미지 업로드
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	//주문내역
	@GetMapping("/order_list")
	public void order_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(5);
		
		log.info("Criteria : " + cri);
		
		List<OrderVO> order_list = adminOrderService.order_list(cri);
		
		
		int totalCount = adminOrderService.getTotalCount(cri);
		
		//페이징
		log.info("카운트:" + new PageDTO(cri, totalCount));
		model.addAttribute("order_list", order_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		log.info("리스트 : " + order_list);
	}
	
	//주문상세정보
	@GetMapping("/order_detail_info")
	public void order_detail_info(Long ord_code, Model model) throws Exception {
		
		//주문자(수령인)정보
		OrderVO vo = adminOrderService.order_info(ord_code);
		
		//주문상세정보
		
		//결제정보
	}
}
