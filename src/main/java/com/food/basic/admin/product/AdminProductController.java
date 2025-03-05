package com.food.basic.admin.product;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.food.basic.admin.category.AdminCategoryService;
import com.food.basic.admin.category.AdminCategoryVO;
import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;
import com.food.basic.common.util.FileManagerUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequestMapping("/admin/product/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminProductController {
	
	private final AdminProductService adminProductService;
	
	private final AdminCategoryService adminCategoryService;
	
	//상품 이미지 업로드
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	//ckeditor 업로드 경로
	@Value("${file.ckdir}")
	private String uploadCKPath;
	
	//상품등록 폼
	@GetMapping("pro_insert")
	public void pro_insertForm(Model model) {
		
		List<AdminCategoryVO> cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("cate_list", cate_list);
	}
	
	//상품등록
	@PostMapping("pro_insert")
	public String pro_insertOk(ProductVO vo, MultipartFile uploadFile) throws Exception {
		
		//상품 이미지 업로드
		String dateFolder = FileManagerUtils.getDateFolder();
		String saveFileName = FileManagerUtils.uploadFile(uploadPath, dateFolder, uploadFile);
		
		vo.setPro_img(saveFileName);
		vo.setPro_up_folder(dateFolder);
		
		log.info("상품정보 : " + vo);
		
		//상품정보 db저장
		adminProductService.pro_insert(vo);
		
		return "redirect:admin/product/pro_list";
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
			
		//상품리스트
		@GetMapping("/pro_list")
		public void pro_list(Criteria cri, Model model) throws Exception {
			
			cri.setAmount(5);
			
			log.info("Criteria : " + cri);
			
			List<ProductVO> pro_list = adminProductService.pro_list(cri);
			//슬래시 변환
			pro_list.forEach(vo -> {
				vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
			});
			
			int totalCount = adminProductService.getTotalCount(cri);
			//페이징
			
			
			log.info("카운트:" + new PageDTO(cri, totalCount));
			model.addAttribute("pro_list", pro_list);
			model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
			
			log.info("리스트 : " + pro_list);
		}
		
		// 상품리스트에서 사용할 이미지보여주기. 1)<img src="매핑주소"> 2) <img src="test.gif">
		@GetMapping("/image_display")
		public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
			return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
    }
		//상품수정 폼
		@GetMapping("pro_edit")
		public void pro_edit(@ModelAttribute("cri") Criteria cri, Integer pro_num, Model model) throws Exception {
			
			//1차 카테고리 목록
			List<AdminCategoryVO> cate_list = adminCategoryService.getFirstCategoryList();
			model.addAttribute("cate_list", cate_list);
			
			//2차 카테고리
			//model이름 : productVO
			ProductVO vo = adminProductService.pro_edit(pro_num);
			
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
			model.addAttribute("productVO", vo);
			
			//1차 카테고리
			int cate_code = vo.getCate_code();
			int cate_pcode = adminCategoryService.getFirstCategoryBySecondCategory(cate_code).getCate_pcode();
			model.addAttribute("cate_pcode", cate_pcode);
			
			//2차 카테고리 목록
			model.addAttribute("sub_cate_list", adminCategoryService.getSecondCategoryList(cate_pcode));
			
		}
		//상품수정하기
		@PostMapping("pro_edit")
		public String pro_edit_ok(ProductVO vo, MultipartFile uploadFile, Criteria cri, RedirectAttributes rttr) throws Exception {
			
			log.info("상품수정정보 : " + vo);
			
			//상품 이미지 변경(업로드) 유무
			if(!uploadFile.isEmpty()) {
				//상품 기존 이미지삭제
				FileManagerUtils.delete(uploadPath, vo.getPro_up_folder(), vo.getPro_img(), "image");
				//변경이미지 업로드
				String dateFolder = FileManagerUtils.getDateFolder();
				String saveFileName = FileManagerUtils.uploadFile(uploadPath, dateFolder, uploadFile);
				//새로운 파일명, 날짜폴더명
				vo.setPro_img(saveFileName);
				vo.setPro_up_folder(dateFolder);
				
				
			}
			//db저장(update)
			adminProductService.pro_edit_ok(vo);
			
			return "redirect:admin/product/pro_list" + cri.getListLink();
		}
		//상품 삭제하기
		@PostMapping("/pro_delete")
		public String pro_delete(Criteria cri, Integer pro_num) throws Exception {
			
			adminProductService.pro_delete(pro_num);
			
			return "redirect:admin/product/pro_list" + cri.getListLink();
		}
		//체크상품 수정작업1
		@PostMapping("/pro_checked_modify1")
		public ResponseEntity<String> pro_checked_modify1(
				@RequestParam("pro_num_arr") List<Integer> pro_num_arr,
				@RequestParam("pro_price_arr") List<Integer> pro_price_arr,
				@RequestParam("pro_discount_arr") List<Integer> pro_discount_arr,
				@RequestParam("pro_disprice_arr") List<Integer> pro_disprice_arr,
				@RequestParam("pro_buy_arr") List<String> pro_buy_arr) throws Exception {
			log.info("상품코드 : " + pro_num_arr);
			log.info("상품가격 : " + pro_price_arr);
			log.info("할인율 : " + pro_discount_arr);
			log.info("할인가 : " + pro_disprice_arr);
			log.info("상품판매 : " + pro_buy_arr);
			
			adminProductService.pro_checked_modify1(pro_num_arr, pro_price_arr, pro_discount_arr, pro_disprice_arr, pro_buy_arr);
			
			ResponseEntity<String> entity = null;
			entity = new ResponseEntity<>("success", HttpStatus.OK);
			
			return entity;
		}
		//체크상품 수정작업2
		@PostMapping("/pro_checked_modify2")
		public ResponseEntity<String> pro_checked_modify2(
				@RequestParam("pro_num_arr") List<Integer> pro_num_arr,
				@RequestParam("pro_price_arr") List<Integer> pro_price_arr,
				@RequestParam("pro_discount_arr") List<Integer> pro_discount_arr,
				@RequestParam("pro_disprice_arr") List<Integer> pro_disprice_arr,
				@RequestParam("pro_buy_arr") List<String> pro_buy_arr) throws Exception {
			log.info("상품코드 : " + pro_num_arr);
			log.info("상품가격 : " + pro_price_arr);
			log.info("할인율 : " + pro_discount_arr);
			log.info("할인가 : " + pro_disprice_arr);
			log.info("상품판매 : " + pro_buy_arr);
			
			adminProductService.pro_checked_modify2(pro_num_arr, pro_price_arr, pro_discount_arr, pro_disprice_arr, pro_buy_arr);
			
			ResponseEntity<String> entity = null;
			entity = new ResponseEntity<>("success", HttpStatus.OK);
			
			return entity;
		}
		
		//상품 상세 + 리뷰관리폼
		@GetMapping("pro_detail")
		public void pro_detail(int pro_num, Model model) throws Exception {
			
			log.info("상품코드 : " + pro_num);
			
			//db연동
			ProductVO vo = adminProductService.pro_detail(pro_num);
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
			
			model.addAttribute("product", vo);
		}
		
}
