package com.myspring.kh.shipping.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.myspring.kh.shipping.vo.ShippingVO;

public interface ShippingDAO {
	public int insertShipping(ShippingVO shippingVO) throws DataAccessException;
	public List<ShippingVO> selectAllShipping(String userId) throws DataAccessException;
	public List<ShippingVO> selectDetailShipping(ShippingVO shippingVO) throws DataAccessException;
	public int nextSeq() throws DataAccessException;
}