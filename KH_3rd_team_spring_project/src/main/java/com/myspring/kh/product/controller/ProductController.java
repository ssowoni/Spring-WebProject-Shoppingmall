package com.myspring.kh.product.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


public interface ProductController {
	public ResponseEntity addProduct(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws Exception;
	public ModelAndView allProduct(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView productDetails(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
