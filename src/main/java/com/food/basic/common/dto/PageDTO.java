package com.food.basic.common.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	
	private int startPage; //각 블럭에서 출력할 시작페이지 번호
	private int endPage; //각 블럭에서 출력할 끝페이지 번호
	
	private boolean prev, next; //이전, 다음 표시여부
	
	private int total; //테이블의 전체 데이터 개수
	
	private Criteria cri; //cri.getPageNum(), cri.getAmount(), cri.getType(), cri.getKeyword()
	
	//생성자
	public PageDTO(Criteria cri, int total) {
		//생성자안에서 위의 5개 필드의 값을 구하는 작업
		this.cri = cri;
		this.total = total;
		
		//블럭마다 보여줄 페이지 개수
		int pageSize = 5;
		int endPageInfo = pageSize - 1; // 10 - 1 = 9
		
		//현재 블럭에서 어떤 페이지 번호를 클릭하든 화면에 페이지 번호를 다시 출력하기 위하여
		//endPage 변수의 값이 동일하게 공식을 처리하는 게 목적
		//총 데이터수가 적으면 endPage의 값이 문제가 된다
		//cri.pageNum() -> 1     (int)(Math.ceil(1 / 10.0)) * 10 => 10
		//cri.pageNum() -> 9       (int)(Math.ceil(9 / 10.0)) * 10 => 10
		//cri.pageNum() -> 10       (int)(Math.ceil(10 / 10.0)) * 10 => 10
		this.endPage = (int) (Math.ceil(cri.getPageNum() / (double) pageSize)) * pageSize;
		
		this.startPage = this.endPage - endPageInfo;
		
		//현재는 총 데이터수에 맞는 endPage의 값을 구하지 못한 상태
		//실제 총 데이터수에 해당하는 블럭의 마지막 페이지 번호
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		
		//아래 조건식이 true이면, endPage 변수의 값은 실제 테이블의 총개수를 이용한 마지막 페이지 번호의 의미가 된다.
		if(realEnd <= this.endPage) {
			this.endPage = realEnd;
		}
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
