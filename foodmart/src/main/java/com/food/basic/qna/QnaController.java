package com.food.basic.qna;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	//qna추가
	@GetMapping("/qna_write")
	public void qna_writeForm() {
		
	}
	
	@PostMapping("/qna_write")
	public String qna_write(QnaVO vo) throws Exception {
		
		log.info("글쓰기 입력 데이터 : " + vo);
		
		qnaService.qna_write(vo);
		return "redirect:/qna/qna_admin_list";
	}
	
	//관리자 qna 내용
	@GetMapping(value = {"qna_content", "qna_update"})
	public void qna_content(int q_num, @ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		
		QnaVO vo = qnaService.qna_content(q_num);
		model.addAttribute("vo", vo);
		
	}
	
	@PostMapping("qna_update")
	public String qna_update(QnaVO vo, Criteria cri) throws Exception{
		
		qnaService.qna_update(vo);
		
		return "redirect:/qna/qna_admin/list" + cri.getListLink();
	}
}
