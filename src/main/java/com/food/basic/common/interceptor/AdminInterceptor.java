package com.food.basic.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.food.basic.admin.AdminVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {
	
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		log.info("preHandle");
		
		boolean result = false;
		
		HttpSession session = req.getSession();
		AdminVO vo = (AdminVO)session.getAttribute("admin_state");
		
		if(vo == null) { // 요청이 인증되지 않은 상태를 의미.
			
			result = false;
			
			if(isAjaxRequest(req)) { // ajax요청이라는 의미.
				response.sendError(400);
			}else {
				// 원래 요청한 주소를 세션으로 저장하는 기능.
				getTargetUrl(req);
				response.sendRedirect("admin/");
			}
		}else {
			result = true;
		}
		
		
		return result;
	}
	
	//원래요청한 주소
	private void getTargetUrl(HttpServletRequest req) {
		
		String uri = req.getRequestURI(); // /userinfo/mypage
		String query = req.getQueryString(); // ?물음표뒤의 문자열.  ?userid=doccomsa
		
		if(query == null || query.equals("null")) {
			query = "";
		}else {
			query = "?" + query;
		}
		
		String targetUrl = uri + query;
		
		if(req.getMethod().equals("GET")) {
			req.getSession().setAttribute("targetUrl", targetUrl);
		}
	}
	
	//사용자 요청이 ajax요청인지 구분
	private boolean isAjaxRequest(HttpServletRequest req) {
		boolean isAjax = false;
		
		String header = req.getHeader("AJAX");
		if(header != null && header.equals("true")) {
			isAjax = true;
		}
		return isAjax;
	}
	
	
}
