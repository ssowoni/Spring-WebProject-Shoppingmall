package com.myspring.kh.product.dao;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.kh.product.vo.ProductVO;

public interface ProductDAO {
	public int insertProduct(Map productMap) throws DataAccessException;
	public List<ProductVO> selectAllProduct() throws DataAccessException;
	public ProductVO selectDetailProduct(String productId) throws DataAccessException;
	public void updateUnitsInStock(ProductVO productVO) throws DataAccessException;
}
