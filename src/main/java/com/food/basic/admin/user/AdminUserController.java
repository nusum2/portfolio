package com.food.basic.admin.user;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;
import com.food.basic.common.util.FileManagerUtils;
import com.food.basic.mail.EmailDTO;
import com.food.basic.mail.EmailService;
import com.food.basic.user.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin/user/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminUserController {
	
	private final AdminUserService adminUserService;
	private final EmailService emailService;
	
	//ckeditor 업로드 경로
	@Value("${file.ckdir}")
	private String uploadCKPath;
	
	//https://www.onedaynet.co.kr 참고
	
	//회원목록
	@GetMapping("/user_list")
	public void user_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(10);
		
		List<UserVO> user_list = adminUserService.user_list(cri);
		
		int userCount = adminUserService.userCount(cri);
		
		//페이징
		model.addAttribute("user_list", user_list);
		model.addAttribute("pageMaker", new PageDTO(cri, userCount));
		log.info("회원 목록 : " + user_list);
	}

	//회원조회
	@GetMapping("/user_info")
	public void user_info(@ModelAttribute("cri") Criteria cri, String u_id, Model model) throws Exception {
		
		UserVO vo = adminUserService.user_info(u_id);
		model.addAttribute("user", vo);
	}
	
	//회원탈퇴
	@PostMapping("/user_delete")
	public String user_delete(Criteria cri, String u_id) throws Exception {
		
		adminUserService.user_delete(u_id);
		
		return "redirect:/admin/user/user_list" + cri.getListLink();
	}
	
	//메일발송 목록
	@GetMapping("/mailinglist")
	public void mailinglist(Criteria cri, String title, Model model) throws Exception {
		cri.setAmount(5);
		
		List<MailMngVO> maillist = adminUserService.getMailInfoList(cri, title);
		
		int totalcount = adminUserService.getMailListCount(title);
		PageDTO pageDto = new PageDTO(cri, totalcount);
		
		model.addAttribute("maillist", maillist);
		model.addAttribute("pageMaker", pageDto);
	}
	
	//일반메서드를 호출 하는 경우에는 파라미터(매개변수)값을 제공해야 한다.
	//주소에 의하여 호출되는 메서드는 파라미터를 스프링이 관여하여, 객체를 먼저 생성한다. 그리고 사용자가 입력한 값이 setter메서드에의하여 객체에 저장된다.
	//매일발송 폼 (CKEditor 사용) - 구분 1.광고/이벤트 2.일반
	@GetMapping("/mailingform")
	public void mailingform(@ModelAttribute("vo") MailMngVO vo) {
		
	}
	
	//메일발송 프로세스
	@PostMapping("/mailingsend")
	public String mailprocess(MailMngVO vo, RedirectAttributes rttr) throws Exception {
		
		log.info("메일내용 : " + vo);
		// 1)메일발송
		// 1.1)회원테이블에서 전체회원 메일정보를 읽어오는 작업 
		String[] emailArr = adminUserService.getALLMailAddress();
		
		EmailDTO dto = new EmailDTO("Foodmart", "Foodmart", "", vo.getTitle(), vo.getContent());
		
		emailService.sendMail(dto, emailArr);
		
		// 메일발송 횟수 업데이트
		adminUserService.mailSendCountUpdate(vo.getIdx());
		
		rttr.addFlashAttribute("msg", "send");
		
		return "redirect:/admin/user/mailinglist";
	}
	
	//메일저장
	@PostMapping("/mailingsave")
	public String mailingsave(@ModelAttribute("vo") MailMngVO vo, Model model, RedirectAttributes rttr) throws Exception {
		
		log.info("메일내용: " + vo);
		
		adminUserService.mailing_save(vo);  // vo 참조(주소)값
		
		log.info("시퀀스: " + vo.getIdx());
		
		model.addAttribute("idx", vo.getIdx()); // 메일보내기 횟수작업 사용
		
//		rttr.addFlashAttribute("msg", "save"); // redirect 사용시 적용됨.
		
		model.addAttribute("msg", "save");
		
		return "admin/user/mailingform"; //리다이렉트 사용 안할 경우에는 주소가 아니라 타임리프 파일명으로 해석된다.
	}
	
	@GetMapping("/mailingsendform")
	public void mailsendform(int idx, Model model) throws Exception {
		
		MailMngVO vo = adminUserService.getMailSendInfo(idx);
		
		model.addAttribute("vo", vo);
	}
	
	@PostMapping("/mailingedit")
	public String mailingedit(@ModelAttribute("vo") MailMngVO vo, Model model) throws Exception {
				
		// db수정작업
		adminUserService.mailingedit(vo);
		
		model.addAttribute("msg", "modify");
		
		return "admin/user/mailingsendform";
	}
	
	//ckeditor 상품설명 이미지 업로드
	//ckeditor 업로드
	//MultipartFile 변수는 파일선택 버튼의 name= 참조
	//req : 클라이언트의 요청 정보를 가지고 있는 객체
	//res : 서버에서 클라이언트에게 보낼 정보를 응답하는 객체
	@PostMapping("/imageupload")
	public void imageupload(HttpServletRequest req, HttpServletResponse res, MultipartFile upload) {
		
		OutputStream out = null;
		PrintWriter printWriter = null; //서버에서 클라이언트에게 응답정보를 보낼때 사용 (업로드한 이미지정보를 브라우저에게 보내는 작업용도)
		
		try {
			String fileName = upload.getOriginalFilename(); //업로드할 클라이언트 파일 이름
			byte[] bytes = upload.getBytes(); //업로드할 파일의 바이트배열
			
			String ckUploadPath = uploadCKPath + fileName; //"C:\\Dev\\upload\\ckeditor\\" + "abc.gif"
			
			out = new FileOutputStream(ckUploadPath); //0byte 파일생성 
			out.write(bytes); //스트림의 공간에 업로드 할 파일의 바이트배열을 채운 상태
			out.flush(); //0byte에서 크기가 채워진 정상적인 파일로 인식
			
			//업로드 할 이미지파일에 대한 정보를 클라이언트에게 보내는 작업
			
			printWriter = res.getWriter();
			
			String fileUrl = "/admin/product/display/" + fileName;
			//String fileUrl = fileName;
			
			//CKeditor 4.12에서는 파일정보를 다음과 같이 만들어 보내야함.
			//{"fileName : "abc.gif", "uploaded" : 1, "url" : "/ckupload/abc.gif"}
			//{"fileName : "변수", "uploaded" : 1, "url" : "변수"}
			printWriter.println("{\"fileName\" :\"" + fileName + "\", \"uploaded\":1, \"url\":\"" + fileUrl + "\"}");
			printWriter.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			if(out != null) {
				try {
					out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(printWriter != null) printWriter.close();
		}
		
	}
	//<img src="매핑주소">
		@GetMapping("/display/{fileName}")
		public ResponseEntity<byte[]> getFile(@PathVariable("fileName")String fileName) {
			
			log.info("파일이미지 : " + fileName);
			
			ResponseEntity<byte[]> entity = null;
			
			try {
				entity = FileManagerUtils.getFile(uploadCKPath, fileName);
			} catch (Exception e) {
				
				e.printStackTrace();
			}

			return entity;
		}
}
