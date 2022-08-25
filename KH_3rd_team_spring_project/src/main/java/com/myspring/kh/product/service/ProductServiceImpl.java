package com.myspring.kh.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.kh.product.vo.ProductVO;
import com.myspring.kh.product.dao.ProductDAO;

@Service("productService")
// ProductServiceImpl 클래스를 이용해 id가 productService 빈을 자동 생성한다.
@Transactional(propagation=Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDAO productDAO;
	
	// 새글 쓰기
	@Override
	public int addProduct(Map productMap) throws Exception{
	return productDAO.insertProduct(productMap);
	}
	
	// 모든 상품 조회
	@Override
	public List<ProductVO> selectAllProduct() throws DataAccessException {
		return productDAO.selectAllProduct();
	}
	
	// 특정 상품 조회
	@Override
	public ProductVO selectDetailProduct(String productId) throws DataAccessException {
		return productDAO.selectDetailProduct(productId);
	}
	
	// 재고 수 감소
	@Override
	public void updateUnitsInStock(ProductVO productVO) throws DataAccessException{
		productDAO.updateUnitsInStock(productVO);
	}
}