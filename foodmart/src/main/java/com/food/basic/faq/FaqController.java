package com.food.basic.faq;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@RequestMapping("/faq/*")
@Slf4j
@Controller
public class FaqController {
	
	private final FaqService faqService;
	
	//qna목록
	@GetMapping("/faq_list")
	public void faq_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(5);
		
		List<FaqVO> faq_list = faqService.faq_list(cri);
		
		int totalCount = faqService.getTotalCount(cri);
		//페이징
		model.addAttribute("faq_list", faq_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		log.info("qna리스트 : " + faq_list);
	}
	
}
