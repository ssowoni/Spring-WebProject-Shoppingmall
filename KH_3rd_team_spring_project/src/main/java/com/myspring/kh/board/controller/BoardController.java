package com.myspring.kh.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface BoardController {
	
	
	public ResponseEntity addNewPost(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws Exception;
	public ModelAndView viewPost(@RequestParam("num") int num, 
									HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ResponseEntity removePost(@RequestParam("num") int num, 
									HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView listBoard(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ResponseEntity addNewComment(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
										throws Exception;
	public ResponseEntity removeComment(@RequestParam("comment_board_num") int comment_board_num,
										@RequestParam("comment_num") int comment_num,
										HttpServletRequest request, HttpServletResponse response) throws Exception;
	

}
