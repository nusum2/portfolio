package com.food.basic.admin.category;

import lombok.Data;

/*
CREATE TABLE CATEGORY_T(
        cate_CODE            NUMBER    PRIMARY KEY,    -- 카테고리 코드
        cate_pcode         NUMBER    NULL,           -- 상위카테고리 코드
        cate_NAME            VARCHAR2(50)    NOT NULL --카테고리 이름
);
*/
@Data
public class AdminCategoryVO {
	
	private Integer cate_code;
	private int cate_pcode;
	private String cate_name;
}
