package com.food.basic.qna;

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
@RequestMapping("/qna/*")
@Slf4j
@Controller
public class QnaController {
	
	private final QnaService qnaService;
	
	//qna목록
	@GetMapping("/qna_list")
	public void qna_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(5);
		
		List<QnaVO> qna_list = qnaService.qna_list(cri);
		
		int totalCount = qnaService.getTotalCount(cri);
		//페이징
		model.addAttribute("qna_list", qna_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		log.info("qna리스트 : " + qna_list);
	}
	
}
