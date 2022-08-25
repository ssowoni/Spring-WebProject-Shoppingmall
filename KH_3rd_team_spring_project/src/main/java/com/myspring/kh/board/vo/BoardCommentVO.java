package com.myspring.kh.board.vo;

import org.springframework.stereotype.Component;

@Component("boardCommentVO")
public class BoardCommentVO {
	
	private String comment_num; // 댓글 번호
	private String comment_board_num; //해당 게시글 번호
	private String comment_content; //댓글 내용
	private String comment_nickname; //댓글 작성자 nickname
	private java.sql.Date comment_date; //댓글 작성일
	
	
	public String getComment_num() {
		return comment_num;
	}
	public void setComment_num(String comment_num) {
		this.comment_num = comment_num;
	}
	public String getComment_board_num() {
		return comment_board_num;
	}
	public void setComment_board_num(String comment_board_num) {
		this.comment_board_num = comment_board_num;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getComment_nickname() {
		return comment_nickname;
	}
	public void setComment_nickname(String comment_nickname) {
		this.comment_nickname = comment_nickname;
	}
	public java.sql.Date getComment_date() {
		return comment_date;
	}
	public void setComment_date(java.sql.Date comment_date) {
		this.comment_date = comment_date;
	} 

}
