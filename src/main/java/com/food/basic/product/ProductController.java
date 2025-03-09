package com.food.basic.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.food.basic.admin.product.ProductVO;
import com.food.basic.common.dto.Criteria;
import com.food.basic.common.dto.PageDTO;
import com.food.basic.common.util.FileManagerUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/product/*")
@RequiredArgsConstructor
@Controller
public class ProductController {
	
	private final ProductService productService;
	
	//상품이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	
	//CKeditor 파일업로드 경로
	@Value("${file.ckdir}")
	private String uploadCKPath;
	
	@GetMapping("/pro_list")
	public void pro_list(@ModelAttribute("cate_code") int cate_code, @ModelAttribute("cate_name") String cate_name, Criteria cri, Model model) throws Exception {
		
		cri.setAmount(5);
		
		log.info("2차 카테고리 코드" + cate_code);
		log.info("2차 카테고리 이름" + cate_name);
		
		List<ProductVO> pro_list = productService.pro_list(cate_code, cri);
		//슬래시 변환
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		int totalCount = productService.getCountProductByCategory(cate_code);
		//페이징
		model.addAttribute("pro_list", pro_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
   }
	
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
	
	//상품정보
	@GetMapping("pro_info")
	public ResponseEntity<ProductVO> pro_info(int pro_num) throws Exception {
		ResponseEntity<ProductVO> entity = null;
		
		ProductVO vo = productService.pro_info(pro_num);
		vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		
		entity = new ResponseEntity<ProductVO>(vo, HttpStatus.OK);
		
				
		return entity;
	}
	
	//상품정보2
	@GetMapping("pro_info_2")
	public void pro_info_2(int pro_num, Model model) throws Exception {
		
		log.info("상품코드 : " + pro_num);
		
		//db연동
		ProductVO vo = productService.pro_info(pro_num);
		vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		
		model.addAttribute("product", vo);
		
	}
	
	//상품화면
	@GetMapping("pro_detail")
	public void pro_detail(int pro_num, Model model) throws Exception {
		
		log.info("상품코드 : " + pro_num);
		
		//db연동
		ProductVO vo = productService.pro_detail(pro_num);
		vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		
		model.addAttribute("product", vo);
	}
}
