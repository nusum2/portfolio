package com.food.basic.admin.carousel;

import java.util.List;

import com.food.basic.common.dto.Criteria;

public interface CarouselMapper {
	
	//관리자 페이지 캐러셀 목록
	List<CarouselVO>carousel_list(Criteria cri);
	
	//데이터 목록 총개수
	int getTotalCount(Criteria cri);
	
	//캐러셀 등록
	void carousel_insert(CarouselVO vo);
	
	//캐러셀 삭제
	void carousel_delete(Integer caro_num);
}
