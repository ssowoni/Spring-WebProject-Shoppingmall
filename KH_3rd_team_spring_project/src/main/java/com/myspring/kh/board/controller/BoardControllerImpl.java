package com.myspring.kh.board.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.kh.board.page.BoardPage;
import com.myspring.kh.board.service.BoardService;
import com.myspring.kh.board.vo.BoardVO;
import com.myspring.kh.user.vo.UserVO;


//스프링 컨테이너가 component-scan에 의해 지정한 클래스를 컨트롤러 빈으로 자동 변환한다. 
@Controller("boardController")
public class BoardControllerImpl implements BoardController{
   //이미지가 저장될 경로 설정 C드라이브 안에 해당 폴더가 있어야한다.
   private static final String BOARD_IMAGE_REPO = "C:\\board\\board_image";
   //@Autowired를 사용하면 별도의 setter나 생성자 없이 속성에 빈을 주입할 수 있다.
   @Autowired
   private BoardService boardService;
   @Autowired
   private BoardVO boardVO;
   @Autowired
   ServletContext application;

   
   //게시글 목록보기 
   @Override
   @RequestMapping(value="/board/listBoard.do", method= {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView listBoard(HttpServletRequest request, HttpServletResponse response) throws Exception{
      
      String viewName = (String)request.getAttribute("viewName");
      //BoardService에 있는 listArticles 메서드 호출
      
      Map<String, Object> map = new HashMap<String,Object>();
      String searchField = request.getParameter("searchField");
      String searchWord = request.getParameter("searchWord");
      if(searchWord != null) {
         map.put("searchField",searchField);
         map.put("searchWord", searchWord);
      }
      int totalCount = boardService.listCount(map);
      
      //--------페이지 처리 start-------------------------------
      //web.xml에 저장되어 있는 값
      //POSTS_PER_PAGE : 10 (페이지당 게시글 수)
      //PAGES_PER_BLOCK : 5 (화면 당 페이지 수)

      //getInitParameter : 파라미터 name에 대한 파라미터 값을 반환한다.
      int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
      int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));

      
      //현재 페이지 확인
      int pageNum=1; //기본값
      String pageTemp = request.getParameter("pageNum");
      if(pageTemp!= null && !pageTemp.equals(""))
         pageNum = Integer.parseInt(pageTemp); //요청 받은 페이지로 수정
      
      //목록에 출력할 게시물 범위 계산
      int start = (pageNum -1) * pageSize +1; // 첫 게시물 번호
      int end = pageNum * pageSize; //마지막 게시물 번호
      map.put("start", start);
      map.put("end", end);
      //-------페이지 처리 end----------------------------
      
      
      List boardLists = boardService.listBoard(map);
      String reqUrl = request.getContextPath()+"/board/listBoard.do";
      String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum,reqUrl); 
      map.put("pagingImg", pagingImg);
      map.put("totalCount", totalCount);
      map.put("pageSize", pageSize);
      map.put("pageNum",pageNum);
      
      ModelAndView mav = new ModelAndView(viewName);
      mav.addObject("boardLists", boardLists);
      mav.addObject("map", map);
      return mav;
   }

   
   
   //게시글 작성
   @Override
   @RequestMapping(value="/board/addNewPost.do", method=  RequestMethod.POST)
   @ResponseBody
   public ResponseEntity addNewPost(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
      multipartRequest.setCharacterEncoding("utf-8");
      Map<String,Object> boardMap = new HashMap<String, Object>();
      //Enumeration : 배열에서 반복문을 이용하여 데이터를 출력하는 것과 같이 반복문을 통해 데이터를 한 번에 출력할 수 있도록 도와준다
      Enumeration enu = multipartRequest.getParameterNames();
      //hasMoreElements:현재 커서 이후에 요소들이 있는지 여부를 체크한다. 
      while(enu.hasMoreElements()) {
         //커서를 다음 요소로 이동시키고, 가리키고 있는 요소 객체를 꺼내 반환한다. 
         String name=(String)enu.nextElement();
         String value=multipartRequest.getParameter(name);
         boardMap.put(name, value);
      }
      
      String imagename = upload(multipartRequest);
      //세션에 저장되어 있는 값을 가져온다.
      HttpSession session = multipartRequest.getSession();
      //세션에서 user에 해당하는 값들을 userVO에 저장 (로그인 되어있는 값)
      UserVO userVO = (UserVO) session.getAttribute("user");
      String nickname = userVO.getNickname();
      boardMap.put("nickname", nickname);
      boardMap.put("imagename", imagename);
      
      String message;
      ResponseEntity resEnt=null;
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.add("Content-Type", "text/html; charset=utf-8");
      try {
         //artucleMap에 저장한 값들을 서비스의 addNewArticle 메서드의 인자로 넣어서 호출한다.
         int num = boardService.addNewPost(boardMap);
         if(imagename!=null && imagename.length()!=0) {
            File srcFile = new File(BOARD_IMAGE_REPO+"\\"+"temp"+"\\"+imagename);
            File destDir = new File(BOARD_IMAGE_REPO+"\\"+num);
            //true로 지정하면 폴더를 생성해준다.
            FileUtils.moveFileToDirectory(srcFile, destDir, true);
         }
         message ="<script>";
         message += "alert('새글을 추가했습니다.');";
         message += " location.href='"+multipartRequest.getContextPath()+"/board/listBoard.do'; ";
         message+="</script>";
         resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
      }catch(Exception e) {
         //만약 오류가 발생한다면 생성한 파일을 삭제해준다.
         File srcFile = new File(BOARD_IMAGE_REPO+"\\"+"temp"+"\\"+imagename);
         srcFile.delete();
         //오류가 발생하면 alert 후 articleForm.do 페이지로 이동
         message ="<script>";
         message += "alert('오류가 발생했습니다. 다시 시도해 주세요');";
         message += " location.href='"+multipartRequest.getContextPath()+"/board/wrtiePost.do'; ";
         message+="</script>";
         resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
         e.printStackTrace();
         
      }
      return resEnt;
   }
   
   
   
   //게시글 상세보기, 댓글 조회하기
   @RequestMapping(value="/board/viewPost.do",method = RequestMethod.GET)
   //매개변수로 넘어온 num 값을 int형의 num 변수에 저장하겠다.
   public ModelAndView viewPost(@RequestParam("num") int num, 
                           HttpServletRequest request, HttpServletResponse response) throws Exception{
      String viewName = (String)request.getAttribute("viewName");
      boardVO = boardService.viewPost(num);
      List commentLists  = boardService.listComment(num);
      ModelAndView mav = new ModelAndView();
      mav.setViewName(viewName);
      mav.addObject("board",boardVO);
      mav.addObject("commentLists", commentLists);
      return mav;
   }
   
   
   //게시글 수정 form 설정
   @RequestMapping(value="/board/modPostForm.do",method = RequestMethod.GET)
   public ModelAndView modPost(@RequestParam("num") int num, 
                           HttpServletRequest request, HttpServletResponse response) throws Exception{
      String viewName = (String)request.getAttribute("viewName");
      //매개변수로 전달받은 num를 서비스의 인자로 전달하겠다.
      boardVO = boardService.viewPost(num);
      ModelAndView mav = new ModelAndView();
      mav.setViewName(viewName);
      mav.addObject("board",boardVO);
      return mav;
   }
   
   
   
   //게시글 수정하기
   @RequestMapping(value="/board/modPost.do", method=RequestMethod.POST)
   @ResponseBody
   public ResponseEntity modPost(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
      multipartRequest.setCharacterEncoding("utf-8");
      Map<String,Object> boardMap = new HashMap<String, Object>();
      //Enumeration : 배열에서 반복문을 이용하여 데이터를 출력하는 것과 같이 반복문을 통해 데이터를 한 번에 출력할 수 있도록 도와준다
      Enumeration enu = multipartRequest.getParameterNames();
      
      while(enu.hasMoreElements()) {
         //form에서 수정한 값들을 가져와 저장한다.
         String name=(String)enu.nextElement();
         String value=multipartRequest.getParameter(name);
         boardMap.put(name, value);
      }//이미지 파일도 수정해서 업로드해야 하므로 MultipartHttpServletRequest 사용해 업로드 한 후 
      
      String imagename = upload(multipartRequest);
      boardMap.put("imagename", imagename);
      //글 정보를 boardMap 에 키/값으로 담아 테이블에 추가한다.
      String num= (String)boardMap.get("num");
      String message;
      ResponseEntity resEnt=null;
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.add("Content-Type", "text/html; charset=utf-8");
      try {//수정된 새 이미지 글번호 폴더에 업로드 후에
         boardService.modPost(boardMap);
         if(imagename!=null && imagename.length()!=0) {
            File srcFile = new File(BOARD_IMAGE_REPO+"\\"+"temp"+"\\"+imagename);
            File destDir = new File(BOARD_IMAGE_REPO+"\\"+num);
            FileUtils.moveFileToDirectory(srcFile, destDir, true);
            
            String originalFileName = (String)boardMap.get("originalFileName");
            File oldFile = new File(BOARD_IMAGE_REPO+"\\"+num+"\\"+originalFileName);
            oldFile.delete();//반드시 oldFile 기존 이미지를 삭제한다.
         }
         message ="<script>";
         message += "alert('글을 수정했습니다..');";
         message += " location.href='"+multipartRequest.getContextPath()+"/board/viewPost.do?num="+num+"';";
         message+="</script>";
         resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
      }catch(Exception e) {
         File srcFile = new File(BOARD_IMAGE_REPO+"\\"+"temp"+"\\"+imagename);
         srcFile.delete();
         message ="<script>";
         message += "alert('오류가 발생했습니다. 다시 수정해주세요');";
         message += " location.href='"+multipartRequest.getContextPath()+"/board/modPost.do?num="+num+"';";
         message+="</script>";
         resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
      }
      return resEnt;
      
   }
   
   //게시글 삭제하기
   @Override
   @RequestMapping(value="/board/removePost.do", method=RequestMethod.POST)
   @ResponseBody
   //매개변수로 넘어온 num값을 int형 변수 num에 저장한다.
   public ResponseEntity removePost(@RequestParam("num") int num,
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
      response.setContentType("text/html; charset=UTF-8");
      String message;
      ResponseEntity resEnt=null;
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.add("Content-Type", "text/html; charset=utf-8");
      try {
         boardService.removeAllComment(num);//게시글 번호에 해당하는 댓글 삭제
         boardService.removePost(num);//게시글 삭제
         File destDir = new File(BOARD_IMAGE_REPO+"\\"+num);
         //아예 해당 게시글 번호의 디렉토리를 삭제해버린다.
         FileUtils.deleteDirectory(destDir);

         message ="<script>";
         message += "alert('글을 삭제했습니다.');";
         message += " location.href='"+request.getContextPath()+"/board/listBoard.do'; ";
         message+="</script>";
         resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
         }catch(Exception e) {
         message ="<script>";
         message += "alert('작업 중 오류가 발생했습니다. 다시 시도해주세요');";
         message += " location.href='"+request.getContextPath()+"/board/viewPost.do?num="+num+"';";
         message+="</script>";
         resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
         e.printStackTrace();
         }
      return resEnt;
   }
      
   //댓글 추가하기 
   @Override
   @RequestMapping(value="/board/addNewComment.do", method=RequestMethod.POST)
   public ResponseEntity addNewComment(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
      multipartRequest.setCharacterEncoding("utf-8");
      //map 형태의 객체를 생성한다.
      Map<String,Object> articleMap = new HashMap<String, Object>();
      //Enumeration : 배열에서 반복문을 이용하여 데이터를 출력하는 것과 같이 반복문을 통해 데이터를 한 번에 출력할 수 있도록 도와준다
      Enumeration enu = multipartRequest.getParameterNames();
      //hasMoreElements:현재 커서 이후에 요소들이 있는지 여부를 체크한다. 
      while(enu.hasMoreElements()) {
         //커서를 다음 요소로 이동시키고, 가리키고 있는 요소 객체를 꺼내 반환한다. 
         //form에서 사용자가 입력한 값을 저장한다. (title, content)
         String name=(String)enu.nextElement();
         String value=multipartRequest.getParameter(name);
         articleMap.put(name, value);
      }
      String num = (String)articleMap.get("comment_board_num");
      String message;
      ResponseEntity resEnt=null;
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.add("Content-Type", "text/html; charset=utf-8");
      
      try {
         boardService.addNewComment(articleMap);
         boardService.plusCommentNum(num);
      message ="<script>";
      message += "alert('댓글을 추가했습니다.');";
      message += " location.href='"+multipartRequest.getContextPath()+"/board/viewPost.do?num="+num+"'; ";
      message+="</script>";
      resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
      }catch(Exception e) {

         message ="<script>";
         message += "alert('오류가 발생했습니다. 다시 시도해 주세요');";
         message += " location.href='"+multipartRequest.getContextPath()+"/board/viewPost.do?num="+num+"'; ";
         message+="</script>";
         resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
         e.printStackTrace();
         
      }
      return resEnt;
      
      
   }
   
   //댓글 삭제하기
   @Override
   @RequestMapping(value="/board/removeComment.do", method=RequestMethod.GET)
   @ResponseBody
   //매개변수로 넘어온 num값을 int형 변수 num에 저장한다.
   public ResponseEntity removeComment(@RequestParam("comment_board_num") int comment_board_num,
                              @RequestParam("comment_num") int comment_num,
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
      response.setContentType("text/html; charset=UTF-8");
      String message;
      ResponseEntity resEnt=null;
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.add("Content-Type", "text/html; charset=utf-8");
      try {
            boardService.removeComment(comment_num);
            boardService.minusCommentNum(comment_board_num);
         message ="<script>";
         message += "alert('댓글을 삭제했습니다.');";
         message += " location.href='"+request.getContextPath()+"/board/viewPost.do?num="+comment_board_num+"'; ";
         message+="</script>";
         resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
         }catch(Exception e) {
         message ="<script>";
         message += "alert('작업 중 오류가 발생했습니다. 다시 시도해주세요');";
         message += " location.href='"+request.getContextPath()+"/board/viewPost.do?num="+comment_board_num+"'; ";
         message+="</script>";
         resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
         e.printStackTrace();
         }
      return resEnt;
   }
   
   
   
   @RequestMapping(value="/board/*.do", method=RequestMethod.GET)
   private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception{
      String viewName = (String)request.getAttribute("viewName");
      ModelAndView mav = new ModelAndView();
      mav.setViewName(viewName);
      return mav;
   }
   
   
   //한 개의 이미지 파일 업로드 처리 메서드
   private String upload(MultipartHttpServletRequest multipartRequest) throws Exception{
      String imagename=null;
      Iterator<String> fileNames = multipartRequest.getFileNames();//첨부된 파일 이름 가져오기
      while(fileNames.hasNext()) {
         String fileName = fileNames.next();
         MultipartFile mFile = multipartRequest.getFile(fileName);
         imagename = mFile.getOriginalFilename();
         File file = new File(BOARD_IMAGE_REPO+"\\"+"temp"+"\\"+fileName);
         if(mFile.getSize()!=0) {//File Null Check
            if(!file.exists()) {//경로상에 파일이 존재하지 않는 경우
               file.getParentFile().mkdirs();//경로에 해당하는 디렉토리들을 생성
               mFile.transferTo(new File(BOARD_IMAGE_REPO+"\\"+"temp"+"\\"+imagename));
               }
            }
         }
      return imagename; // 업로드한 파일이름을 얻은 후 반환
   
   }

   
   
   
   
}

