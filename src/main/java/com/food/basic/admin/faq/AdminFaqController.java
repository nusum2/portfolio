package com.food.basic.admin.faq;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;
import com.food.basic.faq.FaqVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin/faq/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminFaqController {
	
	private final AdminFaqService adminFaqService;
	
	//관리자 페이지 faq목록
	@GetMapping("/faq_admin_list")
	public void faq_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(5);
		
		List<FaqVO> faq_list = adminFaqService.faq_list(cri);
		
		int totalCount = adminFaqService.getTotalCount(cri);
		//페이징
		model.addAttribute("faq_list", faq_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		log.info("faq리스트 : " + faq_list);
	}
		
	//faq추가
	@GetMapping("/faq_write")
	public void faq_writeForm() {
		
	}
	
	@PostMapping("/faq_write")
	public String faq_write(FaqVO vo) throws Exception {
		
		log.info("글쓰기 입력 데이터 : " + vo);
		
		adminFaqService.faq_write(vo);
		return "redirect:/admin/faq/faq_admin_list";
	}
	
	//관리자 faq 내용 불러오기
	@GetMapping(value = {"faq_content", "faq_update"})
	public void faq_content(int q_num, @ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		
		FaqVO vo = adminFaqService.faq_content(q_num);
		model.addAttribute("vo", vo);
		
	}
	//faq 수정
	@PostMapping("faq_update")
	public String faq_update(FaqVO vo, Criteria cri) throws Exception{
		
		adminFaqService.faq_update(vo);
		
		return "redirect:/admin/faq/faq_admin_list" + cri.getListLink();
	}
	
	//faq 삭제
	@GetMapping("faq_delete")
	public String faq_delete(int q_num, Criteria cri) throws Exception {
		
		adminFaqService.faq_delete(q_num);
		
		return "redirect:/admin/faq/faq_admin_list" + cri.getListLink();
	}
		
}
