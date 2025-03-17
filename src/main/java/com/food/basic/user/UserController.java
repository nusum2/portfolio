package com.food.basic.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.food.basic.kakaologin.KakaoUserInfo;
import com.food.basic.mail.EmailDTO;
import com.food.basic.mail.EmailService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/user/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	private final PasswordEncoder passwordEncoder;
	
	private final EmailService emailService;
	
	@GetMapping("join")
	public void joinForm() {
		log.info("join");
	}
	
	@PostMapping("join")
	public String join(UserVO vo) throws Exception {
		
		//비밀번호 암호화
		vo.setU_password(passwordEncoder.encode(vo.getU_password()));
		
		log.info("회원정보 : " + vo);
		userService.join(vo);
		
		
		return "redirect:/user/login";
	}
	
	//아이디 중복체크
	//리턴타입 ResponseEntity 사용시 @ResponseBody 사용할 필요 x, ajax작업은 ResponseEntity 리턴타입 사용 
	@GetMapping("idCheck")
	public ResponseEntity<String> idCheck(String u_id) throws Exception{
		
		log.info("아이디 : " + u_id);
		
		ResponseEntity<String> entity = null;
		
		String idUse = "";
		if(userService.idCheck(u_id) != null) {
			idUse = "no"; //사용불가능
		}else {
			idUse = "yes"; //사용가능
		}
		
		entity = new ResponseEntity<String>(idUse, HttpStatus.OK);
		
		return entity;
	}
			
	@GetMapping("login")
	public void loginForm() {
		
	}
	//파라미터 둘 다 가능 1)LoginDTO 사용  2)String u_id, String u_pwd 사용
	//dto로 묶어서 사용하면 재사용성 높음
	@PostMapping("login") 
	public String loginOk(LoginDTO dto, UserVO lastlogin, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		UserVO vo = userService.login(dto.getU_id());
		
		String msg = "";
		String url = ""; // "/"메인주소
		//Date lastlogin = new Date(session.getLastAccessedTime());
		
		if(vo != null) { //아이디가 존재하는 경우
			//비밀번호 비교 1번자리 평문, 2번자리 암호화
			if(passwordEncoder.matches(dto.getU_password(), vo.getU_password())) { //사용자가 입력한 비밀번호가 암호화된 형태에 해당하는 것이라면
				vo.setU_password("");
				session.setAttribute("login_status", vo);
				userService.user_lastlogin(lastlogin); // 마지막 로그인 시간 기록
				
			}else { //사용자가 입력한 비밀번호가 암호화된 형태에 해당하지 않는 것이라면
				msg = "failPW";
				url = "user/login";
			}
		}else { //아이디가 존재하지 않을 경우
			msg = "failID";
			url = "user/login";
		}
		rttr.addFlashAttribute("msg", msg); //jsp에서 msg변수 출력 목적
				
		return "redirect:/" + url; //메인으로 이동
	}
	
	//로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		
		session.invalidate(); //세션형태로 관리되는 모든 메모리 소멸
		
		
		return "redirect:/";
	}
	//일반 로그인 또는 카카오 로그인 구분
	@GetMapping("mypage")
	public void mypage(HttpSession session, Model model) throws Exception {
		
		if(session.getAttribute("login_status") != null) {
			String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		
			UserVO vo = userService.login(u_id);
			
			model.addAttribute("user", vo);
		}else if(session.getAttribute("kakao_status") != null) {
			KakaoUserInfo kakaoUserInfo = (KakaoUserInfo) session.getAttribute("kakao_status");
			
			//mypage에서 보여줄 정보를 선택적 작업
			UserVO vo = new UserVO();
			vo.setU_email(kakaoUserInfo.getEmail());
			vo.setU_name(kakaoUserInfo.getNickname());
			
			model.addAttribute("user", vo);
			model.addAttribute("msg", "kakao_login");
		}
		
	}
	
	//아이디 찾기 폼
	@GetMapping("/idfind")
	public void idfindForm() {
		
	}
	@PostMapping("/idfind")
	public String idfindOk(String u_name, String u_email, String authcode, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String url = "";
		String msg = "";
		
		//인증코드 확인 작업
		if(authcode.equals(session.getAttribute("authcode"))) {
			//이름과 메일주소를 확인
			String u_id = userService.idfind(u_name, u_email);
			if(u_id != null) {
			    //아이디를 내용으로 메일 발송 작업
			    String subject = "Foodmart 아이디를 보냅니다.";
			    EmailDTO dto = new EmailDTO("Foodmart", "Foodmart", u_email, subject, u_id);
			    
			    emailService.sendMail("emailIDResult", dto, u_id);
			    
			    session.removeAttribute("authcode");
			    
			    msg = "success";
			    url = "user/login";
			    rttr.addFlashAttribute("msg", msg);
			    
			}else {
				msg = "failID";
				url = "user/idfind";
			}
			
			
		}else {
			msg = "failAuthCode";
			url = "user/idfind";
		}
		rttr.addFlashAttribute("msg", msg);
		return "redirect:/" + url;
	}
	
	//비밀번호 찾기 폼
	@GetMapping("/pwfind")
	public void pwfindForm() {
		
	}
	@PostMapping("/pwfind")
	public String pwfindOk(String u_id, String u_name, String u_email, String authcode, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String url = "";
		String msg = "";
		
		if(authcode.equals(session.getAttribute("authcode"))) {
			//사용자가 입력한 3개 정보(아이디, 이름, 이메일)를 조건으로 사용하여, 이메일을 db에서 가져온다
			String d_email = userService.pwfind(u_id, u_name, u_email);
			if(d_email != null) {
				//임시 비밀번호 생성
				String tempPw = userService.getTempPw(); // - 를 제거
				
				//암호화된 비밀번호
				String enc_pw = passwordEncoder.encode(tempPw);
				
				userService.tempPwUpdate(u_id, enc_pw);
				
				EmailDTO dto = new EmailDTO("Foodmart", "Foodmart", d_email, "임시 비밀번호입니다.", tempPw);
				
				emailService.sendMail("emailPwResult", dto, tempPw); //타임리프 파일명
				
				session.removeAttribute("authcode");
				
				msg = "success";
				url = "user/pwfind";
				
			}else {
				url = "user/pwfind";
				msg = "failInput";
			}		
		}else {
			url = "user/pwfind";
			msg = "failAuth";
		}
		rttr.addFlashAttribute("msg", msg);
		return "redirect:/" + url;
	}
	@PostMapping("/modify")
	public String modify(UserVO vo, RedirectAttributes rttr, HttpSession session) throws Exception{
		
		//인터셉터
		if(session.getAttribute("login_status") == null) return "redirect:/user/login";
		
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		vo.setU_id(u_id);
		
		userService.modify(vo);
		
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/user/mypage";
	}
	@GetMapping("/changepw")
	public void changepw() {
		
	}
	@PostMapping("/changepw")
	public String changepw(String cur_pwd, String new_pwd, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		
		UserVO vo = userService.login(u_id);
		
		String msg = "";
		
		if(vo != null) { //아이디가 존재하는 경우
			//비밀번호 비교 
			if(passwordEncoder.matches(cur_pwd, vo.getU_password())) { //사용자가 입력한 비밀번호와 일치시
				//신규 비밀번호 변경 작업
				String u_password = passwordEncoder.encode(new_pwd);
				userService.changePw(u_id, u_password); //암호화된 패스워드 주입
				msg = "success";
				
			}else { //사용자가 입력한 비밀번호가 불일치시
				msg = "failPW";
			}
		}
		rttr.addFlashAttribute("msg", msg); //jsp에서 msg변수 출력 목적
		
		return "redirect:/user/changepw"; //메인으로 이동
	}
	@GetMapping("/delete")
	public void delete() {
		
	}
	@PostMapping("/delete")
	public String delete(String u_password, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		//비밀번호 컬럼 한개만 필요하지만 로그인정보 사용해도 기능 문제 x
		UserVO vo = userService.login(u_id);
		
		String msg = "";
		String url = "";
		
		if(vo != null) { //아이디가 존재하는 경우
			//비밀번호 비교 
			if(passwordEncoder.matches(u_password, vo.getU_password())) { //사용자가 입력한 비밀번호와 일치시
				//삭제 작업
				userService.delete(u_id);
				session.invalidate();
				msg = "success";
				url = "";
				
			}else { //사용자가 입력한 비밀번호가 불일치시
				msg = "failPW";
				url = "user/delete";
			}
		}
		rttr.addFlashAttribute("msg", msg); //jsp에서 msg변수 출력 목적
		
		return "redirect:/" + url;
	}
}
