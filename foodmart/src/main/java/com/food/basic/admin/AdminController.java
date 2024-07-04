package com.food.basic.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/admin/*")
@Slf4j
@Controller
public class AdminController {
	
	private final AdminService adminService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String loginForm() {
		
		return "/admin/adLogin";
	}
	
	@PostMapping("/admin_ok")
	public String admin_ok(AdminVO vo, HttpSession session) throws Exception {
		
		log.info("관리자 정보 : " + vo);
		
		AdminVO d_vo = adminService.loginOk(vo.getAdmin_id());
		String url = "";
		
		if(d_vo != null) {
			if(passwordEncoder.matches(vo.getAdmin_pw(), d_vo.getAdmin_pw())) {
				
				d_vo.setAdmin_pw("");
				session.setAttribute("admin_state", d_vo);
				url = "admin/admin_menu";
			}
		}
		
		return "redirect:/" + url;
	}
	
	@GetMapping("/admin_menu")
	public void admin_menu() {
		
	}
}
