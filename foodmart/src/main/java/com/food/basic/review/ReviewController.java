package com.food.basic.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;
import com.food.basic.common.util.FileManagerUtils;
import com.food.basic.user.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/review/*")
@RequiredArgsConstructor
@Slf4j
@RestController
public class ReviewController {
	
	private final ReviewService reviewService;
	
	//상품 이미지 업로드
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	//이미지 표시
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
   }
	
	//리뷰목록
	@GetMapping("/re_list/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> re_list(@PathVariable("pro_num")int pro_num, @PathVariable("page")int page) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		
		Map<String, Object> map = new HashMap<>();
		//후기 목록
		Criteria cri = new Criteria();
		cri.setAmount(2);
		cri.setPageNum(page);
		List<ReviewVO> re_list = reviewService.re_list(pro_num, cri);
		
		//페이징 정보
		int re_count = reviewService.re_count(pro_num);
		PageDTO pageMaker = new PageDTO(cri, re_count);
		
		map.put("re_list", re_list);
		map.put("pageMaker", pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
				
		return entity;
		
	}
	
	//상품후기 저장 consumes : 클라이언트에서 보내오는 값의 포맷(MIME)
	@PostMapping(value = "/review_save", consumes = {"application/json"}, produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_save(@RequestBody ReviewVO vo, HttpSession session, MultipartFile uploadFile) throws Exception {
		
		String u_id = ((UserVO) session.getAttribute("login_status")).getU_id();
		vo.setU_id(u_id);
		
		//상품 이미지 업로드
		String dateFolder = FileManagerUtils.getDateFolder();
		String saveFileName = FileManagerUtils.uploadFile(uploadPath, dateFolder, uploadFile);
		
		vo.setRe_img(saveFileName);
		vo.setRe_up_folder(dateFolder);
		
		log.info("상품리뷰데이터 : " + vo);
		
		//리뷰 db저장
		reviewService.review_save(vo);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	//리뷰삭제
	@DeleteMapping("/review_delete/{re_code}")
	public ResponseEntity<String> review_delete(@PathVariable("re_code") Long re_code) throws Exception {
		
		log.info("장바구니코드 : " + re_code);
		ResponseEntity<String> entity = null;
		reviewService.review_delete(re_code);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	//리뷰수정폼
	@GetMapping("/review_modify/{re_code}")
	public ResponseEntity<ReviewVO> review_modify(@PathVariable("re_code") Long re_code) throws Exception {
		
		log.info("장바구니코드 : " + re_code);
		ResponseEntity<ReviewVO> entity = null;
		ReviewVO vo = reviewService.review_modify(re_code);
		vo.setRe_up_folder(vo.getRe_up_folder().replace("\\", "/"));
		
		entity = new ResponseEntity<ReviewVO>(reviewService.review_modify(re_code), HttpStatus.OK);
		
		return entity;
	}
	
	//리뷰수정
	@PutMapping("/review_modify")
	public ResponseEntity<String> review_update(@RequestBody ReviewVO vo, MultipartFile uploadFile) throws Exception {
		ResponseEntity<String> entity = null;
		
		//상품 이미지 변경(업로드) 유무
		if(!uploadFile.isEmpty()) {
			//상품 기존 이미지삭제
			FileManagerUtils.delete(uploadPath, vo.getRe_up_folder(), vo.getRe_img(), "image");
			//변경이미지 업로드
			String dateFolder = FileManagerUtils.getDateFolder();
			String saveFileName = FileManagerUtils.uploadFile(uploadPath, dateFolder, uploadFile);
			//새로운 파일명, 날짜폴더명
			vo.setRe_img(saveFileName);
			vo.setRe_up_folder(dateFolder);
			
			
		}
		
		reviewService.review_update(vo);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	//리뷰상세폼
	@GetMapping("/review_detail/{re_code}")
	public ResponseEntity<ReviewVO> review_detail(@PathVariable("re_code") Long re_code) throws Exception {
		
		log.info("장바구니코드 : " + re_code);
		ResponseEntity<ReviewVO> entity = null;
		ReviewVO vo = reviewService.review_detail(re_code);
		vo.setRe_up_folder(vo.getRe_up_folder().replace("\\", "/"));
		
		entity = new ResponseEntity<ReviewVO>(reviewService.review_detail(re_code), HttpStatus.OK);
		
		return entity;
	}
}
