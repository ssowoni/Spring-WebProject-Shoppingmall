package com.myspring.kh.product.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import com.myspring.kh.product.service.ProductService;
import com.myspring.kh.product.vo.ProductVO;
import com.myspring.kh.user.vo.UserVO;

@Controller("productController")
@EnableAspectJAutoProxy
@RequestMapping(value="/product")
public class ProductControllerImpl implements ProductController {
	
	public ProductControllerImpl () {}
	private static final String PRODUCT_IMAGE_REPO = "C:\\product\\product_image";
	private static final Logger logger = LoggerFactory.getLogger(ProductControllerImpl.class);
	@Autowired
	// id가 ProductService인 빈을 자동 주입한다
	private ProductService ProductService;
	@Autowired
	// id가 ProductVO인 빈을 자동 주입한다
	ProductVO ProductVO;
	
	// 폼 이동
	@RequestMapping(value = "/*Form.do", method =  RequestMethod.GET)
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
	
	
	// 새로운 제품 추가하기
	@Override
	@RequestMapping(value="/addProduct.do", method= {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity addProduct(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String,Object> productMap = new HashMap<String, Object>();
		//Enumeration : 배열에서 반복문을 이용하여 데이터를 출력하는 것과 같이 반복문을 통해 데이터를 한 번에 출력할 수 있도록 도와준다
		Enumeration enu = multipartRequest.getParameterNames();
		//hasMoreElements:현재 커서 이후에 요소들이 있는지 여부를 체크한다. 
		while(enu.hasMoreElements()) {
			//커서를 다음 요소로 이동시키고, 가리키고 있는 요소 객체를 꺼내 반환한다. 
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			productMap.put(name, value);
		}
		
		String filename = upload(multipartRequest);
		//세션에 저장되어 있는 값을 가져온다.
		HttpSession session = multipartRequest.getSession();
		//세션에서 user에 해당하는 값들을 userVO에 저장 (로그인 되어있는 값)
		UserVO userVO = (UserVO) session.getAttribute("user");
		String nickname = userVO.getNickname();
		productMap.put("nickname", nickname);
		productMap.put("filename", filename);
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			//artucleMap에 저장한 값들을 서비스의 addNewArticle 메서드의 인자로 넣어서 호출한다.
			int num = ProductService.addProduct(productMap);
			if(filename!=null && filename.length()!=0) {
				File srcFile = new File(PRODUCT_IMAGE_REPO+"\\"+"temp"+"\\"+filename);
				File destDir = new File(PRODUCT_IMAGE_REPO+"\\");
				//true로 지정하면 폴더를 생성해준다.
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
			}
			message ="<script>";
			message += "alert('새글을 추가했습니다.');";
			message += " location.href='"+multipartRequest.getContextPath()+"/product/allProduct.do'; ";
			message+="</script>";
			resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
		}catch(Exception e) {
			//만약 오류가 발생한다면 생성한 파일을 삭제해준다.
			File srcFile = new File(PRODUCT_IMAGE_REPO+"\\"+"temp"+"\\"+filename);
			srcFile.delete();
			//오류가 발생하면 alert 후 articleForm.do 페이지로 이동
			message ="<script>";
			message += "alert('오류가 발생했습니다. 다시 시도해 주세요');";
			message += " location.href='"+multipartRequest.getContextPath()+"/product/addProductForm.do'; ";
			message+="</script>";
			resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
			e.printStackTrace();
			
		}
		return resEnt;
	} 
	//한 개의 이미지 파일 업로드 처리 메서드
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		String filename=null;
		UUID uuid = UUID.randomUUID();
		Iterator<String> fileNames = multipartRequest.getFileNames();//첨부된 파일 이름 가져오기
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			filename = uuid.toString()+"_"+mFile.getOriginalFilename();
			File file = new File(PRODUCT_IMAGE_REPO+"\\"+"temp"+"\\"+fileName);
			if(mFile.getSize()!=0) {//File Null Check
				if(!file.exists()) {//경로상에 파일이 존재하지 않는 경우
					file.getParentFile().mkdirs();//경로에 해당하는 디렉토리들을 생성
					mFile.transferTo(new File(PRODUCT_IMAGE_REPO+"\\"+"temp"+"\\"+filename));
					}
				}
			}
		return filename; // 업로드한 파일이름을 얻은 후 반환
		
	}
	
	// 모든 제품 조회
	@Override
	@RequestMapping(value="/allProduct.do", method=RequestMethod.GET)
	public ModelAndView allProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		
		mav.setViewName(viewName);
		
		List<ProductVO> productList = ProductService.selectAllProduct();
		mav.addObject("productList", productList);

		return mav;
	}
	
	
	// 특정 제품 상세 조회
	@Override
	@RequestMapping(value="/productDetails.do", method=RequestMethod.GET)
	public ModelAndView productDetails(@RequestParam(value="id", required=false) String id,HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProductVO productVO = ProductService.selectDetailProduct(id);
		
		ModelAndView mav = new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		
		mav.addObject("product",productVO);
		mav.setViewName(viewName);
		return mav;
	}
}
