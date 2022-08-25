package com.myspring.kh.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.kh.product.vo.ProductVO;

public interface ProductService {
	public int addProduct(Map ProductMap) throws Exception;
	public List<ProductVO> selectAllProduct() throws DataAccessException;
	public ProductVO selectDetailProduct(String productId) throws DataAccessException;
	public void updateUnitsInStock(ProductVO productVO) throws DataAccessException;
}