package com.myspring.kh.user.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.kh.user.vo.UserVO;

@Repository("userDAO")
// id가 userDAO인 빈을 자동주입
public class UserDAOImpl implements UserDAO {
	@Autowired
	// XML 설정파일에서 생성한 id가 sqlSession인 빈을 자동 주입
	private SqlSession sqlSession;
	
	
	@Override
	public int insertUser(UserVO userVO) throws DataAccessException {
		// 주입된 sqlSession 빈으로 insert() 메소드를 호출
		// sql문에 id와 userVO를 전달한다
		int result = sqlSession.insert("mapper.user.insertUser", userVO);
		return result;
	}
	
	@Override
	public int updateUser(UserVO userVO) throws DataAccessException {
		// 주입된 sqlSession 빈으로 update() 메소드를 호출
		// sql문에 id와 userVO를 전달한다
		int result = sqlSession.update("mapper.user.updateUser", userVO);
		return result;
	}
	
	@Override
	public UserVO loginById(UserVO userVO) throws DataAccessException{
		// 주입된 sqlSession 빈으로 loginById() 메소드를 호출
		// sql문에 id와 userVO를 전달한다
		UserVO vo = sqlSession.selectOne("mapper.user.loginById",userVO);
		return vo;
	}
	
	@Override
	public String checkId(String id) throws DataAccessException{
		// 주입된 sqlSession 빈으로 checkId() 메소드를 호출
		// sql문에 id와 userVO를 전달한다
		String result = sqlSession.selectOne("mapper.user.checkId",id);
		return result;
	}
	
	@Override
	public String checkNickname(String nickname) throws DataAccessException {
		// 주입된 sqlSession 빈으로 checkNickname() 메소드를 호출
		// sql문에 nickname을 전달한다
		String result =  sqlSession.selectOne("mapper.user.checkNickname", nickname);
		return result;
	}
}