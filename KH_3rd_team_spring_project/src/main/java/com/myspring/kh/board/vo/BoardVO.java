package com.myspring.kh.board.vo;

import org.springframework.stereotype.Component;
@Component("boardVO")
public class BoardVO {
	private String num; //1 글번호
	private String title; //2 글 제목
	private String content; //3 글 내용
	private String nickname; //4 작성자 nickname
	private String imagename; //5 이미지 첨부 파일 이름
	private java.sql.Date postdate; //6 글 등록일
	private String commentcount; // 7. 댓글 개수
	
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public java.sql.Date getPostdate() {
		return postdate;
	}
	public void setPostdate(java.sql.Date postdate) {
		this.postdate = postdate;
	}
	public String getCommentcount() {
		return commentcount;
	}
	public void setCommentcount(String commentcount) {
		this.commentcount = commentcount;
	}
}
