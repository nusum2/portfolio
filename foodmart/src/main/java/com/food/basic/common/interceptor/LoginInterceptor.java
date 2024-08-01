package com.food.basic.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.food.basic.user.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;



//인터셉터 용도 클래스
//WebMvcConfig에서 인터셉트할 매핑주소 설정
@Slf4j
@Component //LoginInterceptor bean생성, bean을 관리하는 곳 : 스프링컨테이너 
public class LoginInterceptor implements HandlerInterceptor{
	
	//3개 메소드가 이벤트 특징 가지고 있음
	//순서 매핑주소 요청발생 -> prehandle -> 컨트롤러url 주소 -> posthandle -> afterCompletion
	
	//컨트롤러로 요청이 들어가기전에 가로채어 메소드가 실행
	//리턴값이 true가 되면 컨트롤러로 진행이 이루어짐
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.info("preHandle");
		
		boolean result = false;
		
		HttpSession session = request.getSession();
		UserVO vo = (UserVO)session.getAttribute("login_status");
		
		if(vo == null) { //요청이 인증되지않은 상태를 의미
			result = false;
			
			if(isAjaxRequest(request)) { //ajax요청이라는 의미
				response.sendError(400);
			}else {
				//원래 요청한 주소를 세션으로 저장
				getTargetUrl(request);
				response.sendRedirect("/user/login");
			}
		}else {
			result = true;
		}
		return result;
	}
	
	//원래 요청한 주소
	private void getTargetUrl(HttpServletRequest req) {
		String uri = req.getRequestURI(); // /userinfo/mypage 같은 주소들
		String query = req.getQueryString(); // ?뒤의 문자열 ?userid=
		
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
	//사용자 요청이 ajax인지 구분
	private boolean isAjaxRequest(HttpServletRequest req) {
		boolean isAjax = false;
		
		String header = req.getHeader("AJAX"); //setrequestheader 앞에 있는 변수명 넣기
		if(header != null && header.equals("true")) {
			isAjax = true;
		}
		return isAjax;
	}
	
}
