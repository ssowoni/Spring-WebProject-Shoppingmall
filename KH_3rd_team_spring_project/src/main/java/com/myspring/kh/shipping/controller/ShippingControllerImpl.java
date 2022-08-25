package com.myspring.kh.shipping.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.kh.cart.service.CartService;
import com.myspring.kh.cart.vo.CartVO;
import com.myspring.kh.user.vo.UserVO;
import com.myspring.kh.product.service.ProductService;
import com.myspring.kh.product.vo.ProductVO;
import com.myspring.kh.shipping.service.ShippingService;
import com.myspring.kh.shipping.vo.ShippingVO;

@Controller("shippingController")
@EnableAspectJAutoProxy
@RequestMapping(value="/shipping")
public class ShippingControllerImpl implements ShippingController {
	
	public ShippingControllerImpl() {}
	
	@Autowired
	// id가 ShippingService인 빈을 자동 주입한다
	private ShippingService shippingService;
	@Autowired
	// id가 ShippingVO인 빈을 자동 주입한다
	ShippingVO shippingVO;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	
	@Override
	@RequestMapping(value="/shippingForm.do", method=RequestMethod.GET)
	public ModelAndView shippingForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		String sysdate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("name",userVO.getName());
		dataMap.put("shipping_date",sysdate);
		dataMap.put("address",userVO.getAddress());
		dataMap.put("zipCode",userVO.getZipcode());
		dataMap.put("phoneNum",userVO.getPhonenum());

		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		mav.addObject("dataMap", dataMap);
		mav.setViewName(viewName);

		return mav;
	}
	
	@Override
	@RequestMapping(value="/shippingList.do", method=RequestMethod.GET)
	public ModelAndView shippingList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		String userId = userVO.getId();
		List<ShippingVO> shippingList = shippingService.selectAllShipping(userId);
		mav.addObject("shippingList", shippingList);
		
		return mav;
	}
	
	@Override
	@RequestMapping(value="/shippingView.do", method=RequestMethod.GET)
	public ModelAndView shippingView(@RequestParam("shippingId") int shippingId,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		String userId = userVO.getId();
		
		shippingVO.setShippingId(shippingId);
		shippingVO.setUserId(userId);
		
		List<ShippingVO> shippingView = shippingService.selectDetailShipping(shippingVO);
		mav.addObject("shippingView",shippingView);

		List<ProductVO> productList= new ArrayList<ProductVO>();
		for(int i=0; i<shippingView.size();i++) {
			ShippingVO shipping = shippingView.get(i);
			
			ProductVO product = productService.selectDetailProduct(shipping.getProductId());
			productList.add(product);
		}
		
		mav.addObject("productList", productList);
		return mav;
	}
	
	@Override
	@RequestMapping(value="/addShipping.do", method=RequestMethod.POST)
	public ModelAndView addShipping(@RequestParam Map map, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		String userId = userVO.getId();
		
		shippingVO.setUserId(userId);
		shippingVO.setName(String.valueOf(map.get("name")));
		shippingVO.setZipCode(String.valueOf(map.get("zipCode")));
		shippingVO.setAddress(String.valueOf(map.get("address")));
		shippingVO.setPhoneNum(String.valueOf(map.get("phoneNum")));
		
		java.sql.Date sqlDate = null;
		String sDate = String.valueOf(map.get("shipping_date"));
		try {
			java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
			sqlDate = new java.sql.Date(utilDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		shippingVO.setShipping_date(sqlDate);
		
		shippingService.nextSeq();

		List<CartVO> cartList = cartService.selectAllCart(userId);
		for(int i=0; i<cartList.size(); i++) {
			shippingVO.setProductId(cartList.get(i).getProductId());
			shippingVO.setProductCount(cartList.get(i).getProductCount());
			shippingService.insertShipping(shippingVO);
			
			ProductVO productVO = productService.selectDetailProduct(cartList.get(i).getProductId());
			productVO.setUnitsInStock(productVO.getUnitsInStock()-shippingVO.getProductCount());
			
			productService.updateUnitsInStock(productVO);
		}
		
		cartService.deleteCart(userId);
		ModelAndView mav = new ModelAndView("redirect:/shipping/thankCustomer.do?date="+shippingVO.getShipping_date());
		return mav;
	}

	@RequestMapping(value="/orderConfirmation.do", method=RequestMethod.POST)
	public ModelAndView orderConfirmation(@RequestParam Map map, Model model,
											HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		String userId = userVO.getId();
		
		List<CartVO> cartlist = cartService.selectAllCart(userId);
		
		model.addAttribute("orderData", map);
		mav.addObject("cartlist",cartlist);
		return mav;
	}
	
	@RequestMapping(value="/checkOutCancelled.do", method=RequestMethod.GET)
	public ModelAndView checkOutCancelled(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		
		return mav;
	}
	
	@RequestMapping(value="/thankCustomer.do", method=RequestMethod.GET)
	public ModelAndView thankCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		return mav;
	}
}
