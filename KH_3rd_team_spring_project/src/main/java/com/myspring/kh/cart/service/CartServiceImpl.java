package com.myspring.kh.cart.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.kh.cart.dao.CartDAO;
import com.myspring.kh.cart.vo.CartVO;

@Service("cartService")
// ProductServiceImpl 클래스를 이용해 id가 productService 빈을 자동 생성한다.
@Transactional(propagation=Propagation.REQUIRED)
public class CartServiceImpl implements CartService {
	@Autowired
	private CartDAO cartDAO;
	
	// 새로운 장바구니 추가
	@Override
	public int insertCart(CartVO cartVO) throws DataAccessException {
		return cartDAO.insertCart(cartVO);
	}
	
	// 해당 계정의 장바구니 조회
	@Override
	public List<CartVO> selectAllCart(String userId) throws DataAccessException {
		return cartDAO.selectAllCart(userId);
	}
	
	// 계정의 모든 장바구니 삭제
	@Override
	public void deleteCart(String userId) throws DataAccessException {
		cartDAO.deleteCart(userId);
	}
	
	// 해당 계정의 특정 장바구니
	@Override
	public void removeCart(String userId, String productId) throws DataAccessException {
		cartDAO.removeCart(userId, productId);
	}
	
	// 장바구니 업데이트
	@Override
	public void updateCart(CartVO cartVO) throws DataAccessException{
		cartDAO.updateCart(cartVO);
	}
}