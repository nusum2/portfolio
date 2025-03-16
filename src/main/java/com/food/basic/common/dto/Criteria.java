package com.food.basic.common.dto;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	
	private int pageNum; //1 2 3 4 5 선택된 페이지로
	private int amount; //페이지마다 출력할 게시물 개수
	private int startList; //mysql 페이징용 게시판 시작 번호
	
	//검색종류
	private String type; //선택한 검색종류 1.제목(T) 2.내용(C) 3.작성자(W) 4.제목or내용(TC) 5.제목or작성자(TW) 6.제목or작성자or내용(TWC)
	private String keyword; //검색어
	
	//생성자
	public Criteria() {
		this(1, 10);
	}
	//매개변수가 있는 생성자
	public Criteria(int pageNum, int amount) {
		
		this.pageNum = pageNum;
		this.amount = amount;
		//this.startList = (pageNum - 1) * amount;
		
		//System.out.println("pageNum : " + pageNum + ", amount : " + amount);
	}
	
	public int getStartList() {
		
		return this.startList = (this.pageNum - 1) * this.amount;
		
	}
	
	//아래 메소드 명은 getter메소드 이름 규칙대로 작성해야한다. get + typeArr
	//클라이언트로부터 정보가 (제목 또는 작성자 또는 내용) 선택되어지면 type필드 TWC
	//type.split("") -> "TWC".split("") -> "T" "W" "C"
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}
	//UriComponentsBuilder : 여러개의 파라미터들을 연결하여 URL형태로 만들어주는 기능
	//?pageNum=2&amount=10&type=T&keyword=사과
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.amount)
				.queryParam("type", this.type)
				.queryParam("keyword", this.keyword);
		return builder.toUriString();
	}
}
