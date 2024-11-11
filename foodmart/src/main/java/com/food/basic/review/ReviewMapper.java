package com.food.basic.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.common.dto.Criteria;

public interface ReviewMapper {
	
	List<ReviewVO> re_list(@Param("pro_num")Integer pro_num, @Param("cri") Criteria cri);
	
	int re_count(Integer pro_num);
	
	void review_save(ReviewVO vo);
	
	void review_delete(Long re_code);
	
	ReviewVO review_modify(Long re_code);
	
	void review_update(ReviewVO vo);
	
	ReviewVO review_detail(Long re_code);
}
