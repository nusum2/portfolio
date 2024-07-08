package com.food.basic.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.common.dto.Criteria;

public interface ReviewMapper {
	
	List<ReviewVO> re_list(@Param("pro_num")int pro_num, @Param("cri") Criteria cri);
	
	int re_count(int pro_num);
}
