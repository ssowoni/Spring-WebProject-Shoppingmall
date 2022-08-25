package com.myspring.kh.shipping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.kh.shipping.dao.ShippingDAO;
import com.myspring.kh.shipping.vo.ShippingVO;

@Service("shippingService")
// ProductServiceImpl 클래스를 이용해 id가 shippingService 빈을 자동 생성한다.
@Transactional(propagation=Propagation.REQUIRED)
public class ShippingServiceImpl implements ShippingService {
	@Autowired
	private ShippingDAO shippingDAO;
	
	// 새로운 배송내역 추가
	public int insertShipping(ShippingVO shippingVO) throws DataAccessException{
		return shippingDAO.insertShipping(shippingVO);
	}
	
	// 모든 배송목록 조회
	public List<ShippingVO> selectAllShipping(String userId) throws DataAccessException{
		return shippingDAO.selectAllShipping(userId);
	}
	
	// 특정 배송목록 상세 조회
	public List<ShippingVO> selectDetailShipping(ShippingVO shippingVO) throws DataAccessException{
		return shippingDAO.selectDetailShipping(shippingVO);
	}
	
	// 시퀀스(shippingId) 1 증가
	public int nextSeq() throws DataAccessException {
		return shippingDAO.nextSeq();
	}


}
