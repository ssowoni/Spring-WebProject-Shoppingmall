package com.myspring.kh.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.kh.cart.service.CartService;
import com.myspring.kh.cart.vo.CartVO;
import com.myspring.kh.user.vo.UserVO;
import com.myspring.kh.product.controller.ProductControllerImpl;

@Controller("cartController")
@EnableAspectJAutoProxy
@RequestMapping(value="/cart")
public class CartControllerImpl implements CartController {

	private static final Logger logger = LoggerFactory.getLogger(ProductControllerImpl.class);
	@Autowired
	// id가 CartService인 빈을 자동 주입한다
	private CartService CartService;
	@Autowired
	// id가 CartVO인 빈을 자동 주입한다
	CartVO CartVO;
	
	public CartControllerImpl () {}
	
	// 해당 아이디의 카트 리스트 불러옴
	@Override
	@RequestMapping(value = "/cartPage.do", method =  RequestMethod.GET)
	public ModelAndView cartList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		String id = userVO.getId();
		
		List<CartVO> cartlist = CartService.selectAllCart(id);
		mav.addObject("cartlist", cartlist);

		return mav;
	}
	
	
	// 장바구니에 추가
	@Override
	@RequestMapping(value = "/addCart.do", method = RequestMethod.POST)
	public ModelAndView addCart(@RequestParam(value="productId") String productId,
								@RequestParam(value="unitPrice") Integer unitPrice,
								HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		String userId = userVO.getId();
		
		List<CartVO> cartlist = CartService.selectAllCart(userId);
		
		if(cartlist.size() != 0) { // 사이즈가 0이 아닐 때
			CartVO vo = new CartVO();
			for(int i=0; i<cartlist.size(); i++) {
				vo = cartlist.get(i);
				if(vo.getProductId().equals(productId)) {
					// cartlist 내부에 같은 제품명의 상품이 있는지 체크
					vo.setProductCount(vo.getProductCount()+1);
					vo.setTotalPrice(vo.getTotalPrice()+unitPrice);
					CartService.updateCart(vo);
					break;
				}
			}
			if(!vo.getProductId().equals(productId)) { // 못찾았을 때
				CartVO NewVO = new CartVO();

				NewVO.setUserId(userId);
				NewVO.setProductId(productId);
				NewVO.setProductCount(1);
				NewVO.setTotalPrice(unitPrice);
				CartService.insertCart(NewVO);
			}
			
		} else { // 사이즈가 0일 때
			CartVO NewVO = new CartVO();
			NewVO.setUserId(userId);
			NewVO.setProductId(productId);
			NewVO.setProductCount(1);
			NewVO.setTotalPrice(unitPrice);
			
			CartService.insertCart(NewVO);
		}

		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/product/productDetails.do?id="+productId);
		return mav;
	}
	
	
	// 특정 상품만 삭제
	@Override
	@RequestMapping(value = "/removeCart.do", method = RequestMethod.GET)
	public ModelAndView removeCart(@RequestParam("productId") String productId,HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		String userId = userVO.getId();
		
		List<CartVO> cartlist = CartService.selectAllCart(userId);

		CartVO vo = new CartVO();
			for (int i=0; i < cartlist.size(); i++) {
			vo = cartlist.get(i);
			if(vo.getProductId().equals(productId)) {
				CartService.removeCart(userId,productId);
				cartlist = CartService.selectAllCart(userId);
				break;
			}
		}

		ModelAndView mav = new ModelAndView("redirect:/cart/cartPage.do");
		return mav;
	}
	
	// 모든 상품 삭제
	@Override
	@RequestMapping(value = "/deleteCart.do", method = RequestMethod.GET)
	public ModelAndView deleteCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		String userId = userVO.getId();
		
		CartService.deleteCart(userId);
		
		ModelAndView mav = new ModelAndView("redirect:/cart/cartPage.do");
		return mav;
	}	
}
