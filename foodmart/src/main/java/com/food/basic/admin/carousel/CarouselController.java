package com.food.basic.admin.carousel;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.food.basic.admin.product.AdminProductService;
import com.food.basic.admin.product.ProductVO;
import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;
import com.food.basic.common.util.FileManagerUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/admin/carousel/*")
@RequiredArgsConstructor
@Controller
public class CarouselController {
	
	private final CarouselService carouselService;
	private final AdminProductService adminProductService;
	
	//상품 이미지 업로드
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	//관리자 페이지 캐러셀 목록
	@GetMapping("/carousel_list")
	public void carousel_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(5);
		
		List<CarouselVO> carousel_list = carouselService.carousel_list(cri);
		carousel_list.forEach(vo -> {
			vo.setCaro_up_folder(vo.getCaro_up_folder().replace("\\", "/"));
		});
		
		int totalCount = carouselService.getTotalCount(cri);
		
		//페이징
		model.addAttribute("carousel_list", carousel_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		log.info("캐러셀 리스트 : " + carousel_list);
	}
	
	//리스트에서 사용할 이미지보여주기. 1)<img src="매핑주소"> 2) <img src="test.gif">
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
	
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	    }
	
	//캐러셀 등록폼
	@GetMapping("/carousel_insert")
	public void carousel_insertForm(Criteria cri, Model model) throws Exception {
		
		//상품리스트
		List<ProductVO> pro_list = adminProductService.pro_list(cri);
		//슬래시 변환
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		int totalCount = adminProductService.getTotalCount(cri);
		
		//페이징
		model.addAttribute("pro_list", pro_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		log.info("상품리스트 : " + pro_list);
	}
	
	//캐러셀 등록
	@PostMapping("/carousel_insert")
	public String carousel_insert(CarouselVO vo, MultipartFile uploadFile) throws Exception {
		
		//상품 이미지 업로드
		String dateFolder = FileManagerUtils.getDateFolder();
		String saveFileName = FileManagerUtils.uploadFile(uploadPath, dateFolder, uploadFile);
		
		vo.setCaro_img(saveFileName);
		vo.setCaro_up_folder(dateFolder);
		
		log.info("캐러셀 정보 : " + vo);
		
		//db저장
		carouselService.carousel_insert(vo);
		
		return "redirect:/admin/carousel/carousel_list";
	}
	
	//캐러셀 수정폼
	@GetMapping("/carousel_update")
	public void carousel_updateForm(@ModelAttribute("cri") Criteria cri, Integer caro_num, Model model) throws Exception {
		
		//model명 : caro
		CarouselVO caro_vo = carouselService.carousel_updateForm(caro_num);
		caro_vo.setCaro_up_folder(caro_vo.getCaro_up_folder().replace("\\", "/"));
		model.addAttribute("caro", caro_vo);
		//상품리스트
		List<ProductVO> pro_list = adminProductService.pro_list(cri);
		//슬래시 변환
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		int totalCount = adminProductService.getTotalCount(cri);
		
		//페이징
		model.addAttribute("pro_list", pro_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		log.info("상품리스트 : " + pro_list);
	}
	
	//캐러셀 수정
	@PostMapping("/carousel_update")
	public String carousel_update(CarouselVO vo, MultipartFile uploadFile, Criteria cri) throws Exception {
		
		log.info("상품수정정보 : " + vo);
		
		//상품 이미지 변경(업로드) 유무
		if(!uploadFile.isEmpty()) {
			//상품 기존 이미지삭제
			FileManagerUtils.delete(uploadPath, vo.getCaro_up_folder(), vo.getCaro_img(), "image");
			//변경이미지 업로드
			String dateFolder = FileManagerUtils.getDateFolder();
			String saveFileName = FileManagerUtils.uploadFile(uploadPath, dateFolder, uploadFile);
			//새로운 파일명, 날짜폴더명
			vo.setCaro_img(saveFileName);
			vo.setCaro_up_folder(dateFolder);	
		}
		//db저장
		carouselService.carousel_update(vo);
		
		return "redirect:/admin/carousel/carousel_list" + cri.getListLink();
	}
	
	//캐러셀 삭제
	@PostMapping("/carousel_delete")
	public String carousel_delete(Criteria cri, Integer caro_num) throws Exception {
		
		carouselService.carousel_delete(caro_num);
		
		return "redirect:/admin/carousel/carousel_list" + cri.getListLink();
	}
}
