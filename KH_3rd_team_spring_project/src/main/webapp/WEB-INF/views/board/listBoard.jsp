<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<%
	request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../resources/css/bootstrap.css">  
   <meta charset="UTF-8">
   <title>자유게시판</title>
</head>
<script>
	//글쓰기 버튼 클릭 시 함수 실행
	//만약 isLogOn에 true일 경우(로그인 된 경우)
	function fn_writePost(isLogOn, articleForm, loginForm){
		if(isLogOn!= '' && isLogOn != 'false'){
			location.href=articleForm;
		}else{ //만약 isLogOn에 false(로그아웃)나 값이 없을 경우(url 매핑 초기)
			alert("로그인 후 글쓰기가 가능합니다.")
			location.href=loginForm+'?action=/board/writePost.do';
		}
	}
	
	function fn_viewPost(isLogOn, articleForm, loginForm){
		if(isLogOn!= '' && isLogOn != 'false'){
			location.href=articleForm;
		}else{ //만약 isLogOn에 false(로그아웃)나 값이 없을 경우(url 매핑 초기)
			alert("로그인 후 게시글 상세보기가 가능합니다..")
			location.href=loginForm+'?action=/board/listBoard.do';
		}
	}
</script>
<body>
	
  	
	<div class="jumbotron">
		<div class="container" >
			<h1 class="display-3" style="text-align: center;">자유 게시판</h1>
		</div>
	</div>
	
   <form method="get">
      <table width=100%>
         <tr>
            <td align="center">
               <select name="searchField" id="searchField" class="btn btn-outline-primary">
               	<!-- option 타입으로 ▼ 클릭 시 선택 가능한 것 -->
                  <option value="title">제목</option>
                  <option value="content">내용</option>
               </select>
               <input type="text" name="searchWord" id="searchWord" class="btn btn-outline-primary" />
               <input type="submit" value="검색하기" class="btn btn-outline-primary" />
            </td>
         </tr>
      </table>
   </form>
   
   <!-- 게시글 리스트 -->
   
<div class="container" style="margin-top:60px">
	<div class="card shadow">
		<div class="card-body">
			<table class="table table-hover" id='board_list'>
			<thead class="table-light">
				<tr>						
					<th class="text-center d-none d-md-table-cell">글번호</th>
					<th class="w-50">제목</th>
					<th class="text-center d-none d-md-table-cell">작성자</th>
					<th class="text-center d-none d-md-table-cell">작성날짜</th>
				</tr>
			</thead>
   
      <!-- 목록의내용 -->
     <c:choose>
		<c:when test="${empty boardLists }"> <!-- 게시글 없을 때 -->
         <tr>
            <td colspan="5" align="center">
               등록된 게시물이 없습니다^^*
            </td>
         </tr>
     </c:when>
  	<c:otherwise> <!-- 게시글이 있을 때 -->
  		<!-- forEach 중첩 된 본문 내용(items)을 고정 된 횟수만큼 또는 컬렉션에 반복하는 데 사용되는 반복 태그이다. 
  			varStatus 속성은 인덱스(index)는 물론 반복 횟수(count) 등과 같은 반복 상태에 관련된 정보를 프로퍼티로 알려준다. 
  			인덱스는 0부터 시작-->
  		<c:forEach items="${boardLists }" var="row" varStatus="loop">
      	<tbody>
		<tr>
			<td class="text-center d-none d-md-table-cell">${map.totalCount - (((map.pageNum-1) * map.pageSize) + loop.index)}</td>
			<td><a href="javascript:fn_viewPost('${isLogOn }', '${contextPath }/board/viewPost.do?num=${row.num }', 
								'${contextPath }/user/loginForm.do')" >
			${row.title }</a>
			<c:if test="${row.commentcount != 0}">
				[${row.commentcount}]
			</c:if>
			<td class="text-center d-none d-md-table-cell">${row.nickname }</td>
			<td class="text-center d-none d-md-table-cell">${row.postdate }</td>
			
		</tr>
		</c:forEach>
	</c:otherwise>
	</c:choose>
      </tbody>
  	</table>
   
   
  	 </div>
   </div>
</div>
   
   
<div class="d-none d-md-block">
	<ul class="pagination justify-content-center">
		<li class="page-item">
			${map.pagingImg }
		</li>
	</ul>
</div>

		
<div align="center">
	<!-- 목록 하단의 [글쓰기] 버튼 -->
	<a href="javascript:fn_writePost('${isLogOn }', '${contextPath }/board/writePost.do', 
								'${contextPath }/user/loginForm.do')" class="btn btn-primary">글쓰기</a>
</div>
</body>
</html>
