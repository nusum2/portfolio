package com.food.basic.admin.carousel;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarouselService {
	
	private final CarouselMapper carouselMapper;
	
	public List<CarouselVO>carousel_list(Criteria cri) {
		
		return carouselMapper.carousel_list(cri);
	}
	
	public int getTotalCount(Criteria cri) {
		
		return carouselMapper.getTotalCount(cri);
	}
	
	public void carousel_insert(CarouselVO vo) {
		
		carouselMapper.carousel_insert(vo);
	}
	
	public void carousel_delete(Integer caro_num) {
		
		carouselMapper.carousel_delete(caro_num);
	}
	
	public CarouselVO carousel_updateForm(Integer caro_num) {
		
		return carouselMapper.carousel_updateForm(caro_num);
	}
	
	public void carousel_update(CarouselVO vo) {
		
		carouselMapper.carousel_update(vo);
	}
}
