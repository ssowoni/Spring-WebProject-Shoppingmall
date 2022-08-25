package com.myspring.kh.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.kh.board.vo.BoardVO;




//스프링 컨테이너가 component-scan에 의해 지정한 클래스를 DAO 빈으로 자동 변환한다.
@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO{
	@Autowired
	private SqlSession sqlSession;
	
	//게시글 조회
	@Override
	public List selectListPage(Map<String, Object> map) throws DataAccessException{
		//list 반환형의 객체에 sql 쿼리문을 실행해서 얻은 값을 저장한다. 
		//mybatis.mappers 패키지의 board.xml에서 id값이 selectAllArticlesList인 쿼리문 실행
		List boardLists = sqlSession.selectList("mapper.board.selectListPage",map);
		return boardLists;
	}
	
	
	//게시글 개수
	@Override
	public int selectCount(Map<String, Object> map) throws DataAccessException{
		return sqlSession.selectOne("mapper.board.selectCount",map);
	}
	
	
	//게시글 추가하기
	@Override
	public int insertNewPost(Map articleMap) throws DataAccessException{
		int num = selectNewPostNO();
		articleMap.put("num", num);
		sqlSession.insert("mapper.board.insertNewPost",articleMap);
		return num;
	}
	
	//댓글 추가하기
	@Override
	public void insertNewComment(Map boardMap) throws DataAccessException{
		int comment_num = selectNewCommentNO();
		boardMap.put("comment_num", comment_num);
		sqlSession.insert("mapper.board.insertNewComment",boardMap);
	}
	
	//댓글 수 증가
	@Override
	public void plusCommentNum(String num)  throws DataAccessException{
		sqlSession.update("mapper.board.plusCommentNum",num);
	}

	//댓글 삭제
	public void deleteComment(int comment_num) throws DataAccessException{
		sqlSession.delete("mapper.board.deleteComment",comment_num);
	}
	//댓글 수 감소
	public void minusCommentNum(int comment_board_num) throws DataAccessException{
		sqlSession.update("mapper.board.minusCommentNum",comment_board_num);
	}


	//해당 번호의 게시글 조회
	@Override
	public BoardVO selectViewPost(int num) throws DataAccessException{
		//T selectOne(query_id, 조건 ) : id에 대한 select문을 실행하면서 사용되는 조건도 전달한다.
		return sqlSession.selectOne("mapper.board.selectViewPost",num);
	}
	
	//게시글에 해당하는 댓글 조회
	@Override
	public List selectComment(int num) throws DataAccessException{
		List commentLists = sqlSession.selectList("mapper.board.selectComment",num);
		return commentLists;
	}
	
	
	
	//게시글 수정
	@Override
	public void updatePost(Map boardMap) throws DataAccessException{
		sqlSession.update("mapper.board.updatePost",boardMap);
	}
	
	//게시글 삭제
	@Override
	public void deletePost(int num) throws DataAccessException{
		sqlSession.delete("mapper.board.deletePost",num);
	}
	
	//게시글 번호에 해당하는 댓글 전체 삭제
	@Override
	public void deleteAllComment(int num) throws DataAccessException{
		sqlSession.delete("mapper.board.deleteAllComment",num);
	}


	private int selectNewPostNO()  throws DataAccessException{
		return sqlSession.selectOne("mapper.board.selectNewPostNO");
	}//새 글 번호를 가져온다.
	
	private int selectNewCommentNO() {
		return sqlSession.selectOne("mapper.board.selectNewCommentNO");
	}//새 댓글 번호를 가져온다.

}
