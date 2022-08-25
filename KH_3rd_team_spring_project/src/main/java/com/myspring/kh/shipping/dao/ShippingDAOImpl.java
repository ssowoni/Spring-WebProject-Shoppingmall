package com.myspring.kh.shipping.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.kh.product.vo.ProductVO;
import com.myspring.kh.shipping.vo.ShippingVO;
import com.myspring.kh.product.dao.ProductDAO;

@Repository("shippingDAO")
public class ShippingDAOImpl implements ShippingDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 새로운 배송내역 추가
	public int insertShipping(ShippingVO shippingVO) throws DataAccessException{
		// 주입된 sqlSession 빈으로 insert() 메소드를 호출
		// sql문에 shippingVO 전달한다
		int result = sqlSession.insert("mapper.shipping.insertShipping", shippingVO);
		return result;
	}
	
	// 모든 배송목록 조회
	public List<ShippingVO> selectAllShipping(String userId) throws DataAccessException{
		// 주입된 sqlSession 빈으로 select() 메소드를 호출
		List<ShippingVO> result = sqlSession.selectList("mapper.shipping.selectAllShipping", userId);
		return result;
	}
	
	// 특정 배송목록 상세 조회
	public List<ShippingVO> selectDetailShipping(ShippingVO shippingVO) throws DataAccessException{
		List<ShippingVO> result = sqlSession.selectList("mapper.shipping.selectDetailShipping", shippingVO);
		return result;
	}
	
	// 시퀀스 1 증가
	public int nextSeq() throws DataAccessException{
		int result = sqlSession.selectOne("mapper.shipping.nextSeq");
		return result;
	}
}