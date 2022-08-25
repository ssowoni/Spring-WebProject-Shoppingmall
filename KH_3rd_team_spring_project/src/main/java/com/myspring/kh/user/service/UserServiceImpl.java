package com.myspring.kh.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.kh.user.dao.UserDAO;
import com.myspring.kh.user.vo.UserVO;

@Service("userService")
// userServiceImpl 클래스를 이용해 id가 userService 빈을 자동 생성한다.
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	
	// 새로운 회원 추가
	@Override
	public int addUser(UserVO userVO) throws DataAccessException {
		return userDAO.insertUser(userVO);
	}
	
	// 회원 정보 수정
	@Override
	public int editUser(UserVO userVO) throws DataAccessException {
		return userDAO.updateUser(userVO);
	}
	
	// 회원 로그인
	@Override
	public UserVO login(UserVO userVO) throws Exception{
		return userDAO.loginById(userVO);
	}
	
	// 회원 가입 아이디 중복 체크
	@Override
	public String checkId(String id) throws Exception{
		return userDAO.checkId(id);
	}
	
	// 회원 가입 닉네임 중복 체크
	@Override
	public String checkNickname(String nickname) throws DataAccessException {
		return userDAO.checkNickname(nickname);
	}
}