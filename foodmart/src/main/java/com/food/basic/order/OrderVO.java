package com.food.basic.order;

import java.util.Date;

import lombok.Data;

/*
 ORD_CODE            NUMBER, --주문 번호
        u_ID             VARCHAR2(15)            NOT NULL, --유저아이디
        ORD_NAME            VARCHAR2(30)            NOT NULL, --주문자 이름
        ORD_zip_code        CHAR(5)                 NOT NULL, --우편번호
        ORD_ADDR      VARCHAR2(50)            NOT NULL, --주소
        ORD_deADDR     VARCHAR2(50)            NOT NULL, --상세주소
        ORD_phone             VARCHAR2(20)            NOT NULL, --전화번호
        ORD_PRICE           NUMBER                  NOT NULL,  -- 총주문금액. 선택
        ORD_REGDATE         DATE DEFAULT SYSDATE    NOT NULL --주문일
*/
@Data
public class OrderVO {
	
	private Long ord_code;
	private String u_id;
	private String ord_name;
	private String ord_zip_code;
	private String ord_addr;
	private String ord_deaddr;
	private String ord_phone;
	private int ord_price;
	private String ord_desc;
	private Date ord_regdate;
}
