package com.myspring.kh.product.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.kh.product.vo.ProductVO;

@Repository("productDAO")
public class ProductDAOImpl implements ProductDAO {
	
	@Autowired
	private SqlSession sqlSession;

	//상품 추가하기
	@Override
	public int insertProduct(Map productMap) throws DataAccessException{
		int result = sqlSession.insert("mapper.product.insertProduct",productMap);
		return result;
	}
	
	//모든 상품 불러오기
	@Override
	public List<ProductVO> selectAllProduct() throws DataAccessException {
		// 주입된 sqlSession 빈으로 select() 메소드를 호출
		List<ProductVO> result = sqlSession.selectList("mapper.product.selectAllProduct");
		return result;
	}
	
	//상품 상세조회
	@Override
	public ProductVO selectDetailProduct(String productId) throws DataAccessException {
		// 주입된 sqlSession 빈으로 select() 메소드를 호출
		// sql문에 productId를 전달
		ProductVO result = sqlSession.selectOne("mapper.product.selectDetailProduct", productId);
		return result;
	}
	
	//재고수 업데이트
	@Override
	public void updateUnitsInStock(ProductVO productVO) throws DataAccessException {
		sqlSession.update("mapper.product.updateUnitsInStock", productVO);
	}
}