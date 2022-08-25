package com.myspring.kh.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.kh.board.dao.BoardDAO;
import com.myspring.kh.board.vo.BoardVO;



//스프링 컨테이너가 component-scan에 의해 지정한 클래스를 서비스 빈으로 자동 변환한다.
@Service("boardService")
//REQUIRED : 부모 트랜잭션 내에서 실행하며 부모 트랜잭션이 없을 경우 새로운 트랜잭션을 생성
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardDAO boardDAO;
	
	public List listBoard(Map<String, Object> map) throws Exception{
		//List형의 객체에 boardDAO의 메서드를 실행해서 조회된 쿼리 값들을 전달한다.
		List boardLists = boardDAO.selectListPage(map);
		return boardLists;
	}
	
	@Override
	public int listCount(Map<String, Object> map) throws Exception{
		return boardDAO.selectCount(map);
	}
	
	// 새글 쓰기
	@Override
	public int addNewPost(Map boardMap) throws Exception{
		return boardDAO.insertNewPost(boardMap);
	}
	
	//댓글 추가하기 
	@Override
	public void addNewComment(Map boardMap) throws Exception{
		boardDAO.insertNewComment(boardMap);
	}
	
	//댓글 수 증가
	@Override
	public void plusCommentNum(String num) throws Exception{
		boardDAO.plusCommentNum(num);
	}
	
	//댓글 삭제
	public void removeComment(int comment_num) throws Exception{
		boardDAO.deleteComment(comment_num);
	}
	
	//board 테이블 댓글 수 감소
	public void minusCommentNum(int comment_board_num) throws Exception{
		boardDAO.minusCommentNum(comment_board_num);
	}
	
	//게시글 상세보기
	@Override
	public BoardVO viewPost(int num) throws Exception{
		BoardVO PostVO = boardDAO.selectViewPost(num);
		return PostVO;
	}
	
	//댓글 보여주기
	@Override
	public List listComment(int num) throws Exception{
		List commentLists = boardDAO.selectComment(num);
		return commentLists;
		
	}
	
	//게시글 수정하기
	@Override
	public void modPost(Map boardMap) throws Exception{
		boardDAO.updatePost(boardMap);
	}
	
	//게시글 삭제하기
	@Override
	public void removePost(int num) throws Exception{
		boardDAO.deletePost(num);
	}
	
	//게시글 번호에 해당하는 댓글 전체 삭제
	@Override
	public void removeAllComment(int num) throws Exception{
		boardDAO.deleteAllComment(num);
	}
}
