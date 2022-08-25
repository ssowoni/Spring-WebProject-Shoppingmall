package com.myspring.kh.user.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.kh.user.service.UserService;
import com.myspring.kh.user.vo.UserVO;

@Controller("userController")
//@EnableAspectJAutoProxy
// userControllerImpl 클래스에 대해 id가 userController인 빈을 자동 생성
public class UserControllerImpl implements UserController {
	// LoggerFactory 클래스를 이용해 Logger 클래스 객체를 가져온다
	private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
	@Autowired
	// id가 userService인 빈을 자동 주입한다
	private UserService userService;
	@Autowired
	// id가 userVO인 빈을 자동 주입한다
	UserVO userVO;
	
	@RequestMapping(value = { "/main.do"}, method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	private ModelAndView omain(HttpServletRequest request, HttpServletResponse response) {
		//String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main");
		return mav;
	}
	@RequestMapping(value = { "/chat.do"}, method = RequestMethod.GET)
	private ModelAndView chat(HttpServletRequest request, HttpServletResponse response) {
		//String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chat");
		return mav;
	}
	@RequestMapping(value = { "/admin.do"}, method = RequestMethod.GET)
	private ModelAndView adminchat(HttpServletRequest request, HttpServletResponse response) {
		//String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin");
		return mav;
	}
	
	// 새로운 회원 삽입하기
	@Override
	@RequestMapping(value="/user/addUser.do", method=RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute("user") UserVO user,RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 회원가입창에서 전송된 회원정보(user)를 바로 userVO 객체에 설정한다.
		request.setCharacterEncoding("UTF-8");
		ModelAndView mav = new ModelAndView();
		try { // String에 NullPointException error가 발생하므로 try~chatch문 안으로 작성
			String id = request.getParameter("id");
			String nickname = request.getParameter("nickname");//이부분이 값이 안 받아짐
			String resultI = userService.checkId(id);
			String resultN = userService.checkNickname(nickname);
			if(resultI.equals("1")) {
				rAttr.addAttribute("result", "idDuplicate"); // 회원가입 아이디 중복 시 실패 알림 발생
				mav.setViewName("redirect:/user/signupForm.do"); // 회원가입페이지로 다시 이동
			} else if (resultN.equals("1")) {
				rAttr.addAttribute("result", "nicknameDuplicate"); // 회원가입 닉네임 중복 시 실패 알림 발생
				mav.setViewName("redirect:/user/signupForm.do"); // 회원가입페이지로 다시 이동
			} else {
				userService.addUser(user);
				mav.setViewName("redirect:/user/signupCompleteForm.do"); // 회원가입완료 페이지로 이동
			}
		}catch (Exception e) {}
		
		return mav;
	}
	
	// 회원 정보 수정하기
	@Override
	@RequestMapping(value="/user/editUser.do", method=RequestMethod.POST)
	public ModelAndView editUser(@ModelAttribute("user") UserVO user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 회원가입창에서 전송된 회원정보(user)를 바로 userVO 객체에 설정한다.
		request.setCharacterEncoding("UTF-8");
		
		// int result = 0; result = 이거 없어도 될거같아서 한번 실험해봄
		userService.editUser(user);
		HttpSession session = request.getSession();
		session.removeAttribute("user"); // 로그아웃 요청시 세션에 저장된 로그인 정보와 회원정보 삭제
		session.removeAttribute("isLogOn");
		// 회원정보 추가 후 ModelAndView 클래스의 redirect 속성을 이용
		// /user/listusers.do로 리다이렉트 한다
		ModelAndView mav = new ModelAndView("redirect:/user/editCompleteForm.do");
		return mav;
	}
	
	// 로그인 코드
	@Override
	@RequestMapping(value="/user/login.do",method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("user") UserVO user,
			RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		userVO = userService.login(user);
		//String back = request.getHeader("referer");
		// login() 메소드를 호출하면서 로그인 정보를 전달
		if(userVO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user",userVO);
			session.setAttribute("isLogOn", true);
			mav.setViewName("redirect:/main.do");
		} else {
			rAttr.addAttribute("result", "loginFailed"); // 로그인 실패시 실패 메세지를 로그인 창으로 전달
			mav.setViewName("redirect:/user/loginForm.do"); // 다시 로그인 창으로 리다이렉트
		}
		return mav;
	}
	
	@Override
	@RequestMapping(value="/user/logout.do",method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		session.removeAttribute("user"); // 로그아웃 요청시 세션에 저장된 로그인 정보와 회원정보 삭제
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/main.do");
		return mav;
	}
	
	
	// 데이터베이스 연동 작업이 없는 입력창 요청시. 뷰 이름만 ModelAndView로 반환한다
	// 정규식을 이용해 요청명이 Form.do로 끝나면 form() 메소드를 호출한다
	// 로그인 창 요청시 매개변수 result가 전송되면 변수 result에 값을  저장
	// 최초로 로그인창을 요청할 때는 result가 전송되지 않는다.
	
	@RequestMapping(value = "/user/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
							  @RequestParam(value= "action", required=false) String action,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		HttpSession session = request.getSession(); 
		session.setAttribute("action", action);  
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName(viewName);
		return mav;
	}


	// 파일 이름 가져오기
	private String getViewName(HttpServletRequest request) throws Exception {
        String contextPath = request.getContextPath();
        String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        if (uri == null || uri.trim().equals("")) {
            uri = request.getRequestURI();
        }
 
        int begin = 0;
        if (!((contextPath == null) || ("".equals(contextPath)))) {
            begin = contextPath.length();
        }
 
        int end;
        if (uri.indexOf(";") != -1) {
            end = uri.indexOf(";");
        } else if (uri.indexOf("?") != -1) {
            end = uri.indexOf("?");
        } else {
            end = uri.length();
        }
 
        String viewName = uri.substring(begin, end);
        if (viewName.indexOf(".") != -1) {
            viewName = viewName.substring(0, viewName.lastIndexOf("."));
        }
        if (viewName.lastIndexOf("/") != -1) {
            viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
            //user/listusers.do로 요청할 경우 user/listusers를 파일이름으로 가져옴
        }
        return viewName;
    }
}