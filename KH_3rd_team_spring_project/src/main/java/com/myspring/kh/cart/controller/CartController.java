package com.myspring.kh.cart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


public interface CartController {
	public ModelAndView cartList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView addCart(@RequestParam(value="productId") String productId, @RequestParam(value="unitPrice") Integer unitPrice, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView deleteCart(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView removeCart(@RequestParam("productId") String productId,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
