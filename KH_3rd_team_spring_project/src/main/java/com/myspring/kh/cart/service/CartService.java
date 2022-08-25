package com.myspring.kh.cart.service;

import java.util.List;


import org.springframework.dao.DataAccessException;

import com.myspring.kh.cart.vo.CartVO;


public interface CartService {
	public int insertCart(CartVO cartVO) throws DataAccessException;
	public List<CartVO> selectAllCart(String userId) throws DataAccessException;
	public void removeCart(String productId, String userId) throws DataAccessException;
	public void deleteCart(String userId) throws DataAccessException;
	public void updateCart(CartVO cartVO) throws DataAccessException;

}