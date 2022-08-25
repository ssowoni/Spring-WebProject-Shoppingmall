package com.myspring.kh.cart.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.kh.cart.vo.CartVO;


@Repository("cartDAO")
public class CartDAOImpl implements CartDAO {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public int insertCart(CartVO cartVO) throws DataAccessException {
		// 주입된 sqlSession 빈으로 insert() 메소드를 호출
		// sql문에 productVO 전달한다
		int result = sqlSession.insert("mapper.cart.insertCart", cartVO);
		return result;
	}
	
	@Override
	public List<CartVO> selectAllCart(String userId) throws DataAccessException {
		// 주입된 sqlSession 빈으로 select() 메소드를 호출
		List<CartVO> result = sqlSession.selectList("mapper.cart.selectAllCart", userId);
		return result;
	}
	
	@Override
	public void removeCart(String userId, String productId) throws DataAccessException{
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId",userId);
		map.put("productId",productId);
		sqlSession.delete("mapper.cart.removeCart",map);
	}
	
	public void deleteCart(String userId) throws DataAccessException{
		sqlSession.delete("mapper.cart.deleteCart",userId);
	}
	
	@Override
	public void updateCart(CartVO cartVO) throws DataAccessException{
		sqlSession.update("mapper.cart.updateCart",cartVO);
	}
}