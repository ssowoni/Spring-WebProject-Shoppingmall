package com.myspring.kh.board.service;

import java.util.List;
import java.util.Map;

import com.myspring.kh.board.vo.BoardVO;



public interface BoardService {
	public int addNewPost(Map BoardMap) throws Exception; // 새글 쓰기
	public BoardVO viewPost(int num) throws Exception; //게시글 상세보기
	public void modPost(Map BoardMap) throws Exception;//게시글 수정하기
	public void removePost(int num) throws Exception;//게시글 삭제하기
	public int listCount(Map<String, Object> map) throws Exception;//게시글 개수 구하기
	public List listBoard(Map<String, Object> map) throws Exception;//게시글 목록 조회
	public List listComment(int num) throws Exception;//댓글 목록 조회
	public void addNewComment(Map BoardMap) throws Exception;//새 댓글 추가
	public void plusCommentNum(String num) throws Exception;//board 테이블 댓글 수 증가
	public void removeComment(int comment_num) throws Exception;//댓글 삭제
	public void minusCommentNum(int comment_board_num) throws Exception;//board 테이블 댓글 수 감소
	public void removeAllComment(int num) throws Exception;//게시글 번호에 해당하는 댓글 전체 삭제
	
}
