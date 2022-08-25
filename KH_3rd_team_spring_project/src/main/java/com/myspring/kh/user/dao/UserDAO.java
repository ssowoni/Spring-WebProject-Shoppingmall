package com.myspring.kh.user.dao;

import org.springframework.dao.DataAccessException;

import com.myspring.kh.user.vo.UserVO;

public interface UserDAO {
	public int insertUser(UserVO userVO) throws DataAccessException;
	public int updateUser(UserVO userVO) throws DataAccessException;
	public UserVO loginById(UserVO userVO) throws DataAccessException;
	public String checkId(String id) throws DataAccessException;
	public String checkNickname(String nickname) throws DataAccessException;
}