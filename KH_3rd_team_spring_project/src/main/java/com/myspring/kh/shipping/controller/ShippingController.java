package com.myspring.kh.shipping.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.kh.shipping.vo.ShippingVO;


public interface ShippingController {
	public ModelAndView shippingForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView shippingList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView shippingView(@RequestParam("shippingId") int shippingId,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView addShipping(@RequestParam Map map, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
