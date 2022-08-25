package com.myspring.kh.user.service;

import org.springframework.dao.DataAccessException;

import com.myspring.kh.user.vo.UserVO;

public interface UserService {
	public int addUser(UserVO userVO) throws DataAccessException;
	public int editUser(UserVO userVO) throws DataAccessException;
	public UserVO login(UserVO userVO) throws Exception;
	public String checkId(String id) throws Exception;
	public String checkNickname(String nickname) throws DataAccessException;
}