package com.myspring.kh.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.kh.board.vo.BoardVO;



public interface BoardDAO {
	public List selectListPage(Map<String, Object> map) throws DataAccessException;
	public int insertNewPost(Map articleMap) throws DataAccessException;
	
	public BoardVO selectViewPost(int num) throws DataAccessException;
	public void updatePost(Map boardMap) throws DataAccessException;
	public void deletePost(int num) throws DataAccessException;
	
	public int selectCount(Map<String, Object> map) throws DataAccessException;
	public List selectComment(int num) throws DataAccessException;
	public void insertNewComment(Map boardMap) throws DataAccessException;
	public void plusCommentNum(String num)  throws DataAccessException;
	public void deleteComment(int comment_num) throws DataAccessException;
	public void minusCommentNum(int comment_board_num) throws DataAccessException;
	public void deleteAllComment(int num) throws DataAccessException;


}
