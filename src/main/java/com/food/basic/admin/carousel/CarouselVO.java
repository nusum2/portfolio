package com.food.basic.admin.carousel;

import java.util.Date;

import lombok.Data;

//caro_num, pro_num, caro_title, caro_content, caro_up_folder, caro_img

@Data
public class CarouselVO {
	
	private Integer caro_num; //캐러셀번호
	private Integer pro_num; //상품번호
	private String pro_name; //상품명
	private String caro_content; //제목
	private String caro_up_folder; //이미지 업로드 폴더
	private String caro_img; //이미지
	private Date caro_date; //등록일
	private Date caro_up_date; //수정일
}
