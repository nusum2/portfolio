package com.food.basic.admin.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.food.basic.common.dto.Criteria;
import com.food.basic.review.ReviewVO;

public interface AdminReviewMapper {
	
	//리뷰목록
	List<ReviewVO> re_list(@Param("pro_num")Integer pro_num, @Param("cri") Criteria cri);
	//리뷰 리스트 카운트
	int re_count(Integer pro_num);
	//리뷰 삭제
	void review_delete(Long re_code);
	//리뷰 상세폼
	ReviewVO review_detail(Long re_code);
}
