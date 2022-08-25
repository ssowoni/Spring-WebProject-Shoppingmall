<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
 <script type="text/javascript">
   function joinChating() {
      window.open("${contextPath}/chat.do", "chating", "width=640, height=400")
   }
   function adminChating() {
      window.open("${contextPath}/admin.do", "admin", "width=640, height=400")
   }
</script>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<div class="container-fluid">
			<a class="navbar-brand" href="${contextPath}/main.do">Home</a>
		</div>
		<div class="col-md-8 text-end">
			<c:choose>
         	 <c:when test="${isLogOn != true  && membe == null}">
         		   <a href="${contextPath}/user/loginForm.do"
						class="btn btn-secondary">Login</a>
					<a href="${contextPath}/user/signupForm.do"
						class="btn btn-secondary">Sign-up</a>
					<a href="${contextPath}/product/allProduct.do" class="btn btn-secondary">상품 목록</a>
					<a href="${contextPath}/board/listBoard.do" class="btn btn-secondary">게시판</a>
					<a href="javascript:joinChating()" class="btn btn-secondary">채팅</a>
         		 </c:when>
        		  <c:otherwise>
	      		  <strong style="color: white;">${user.nickname } 님</strong>
					<a href="${contextPath}/user/logout.do" class="btn btn-secondary">Logout</a>
					<a href="${contextPath}/user/userEditForm.do"
						class="btn btn-secondary">Edit</a>
					<a href="${contextPath}/product/allProduct.do" class="btn btn-secondary">상품 목록</a>
					<a href="${contextPath}/shipping/shippingList.do" class="btn btn-secondary">배송 목록</a>
					<a href="javascript:joinChating()" class="btn btn-secondary">채팅</a>
					<a href="${contextPath}/board/listBoard.do" class="btn btn-secondary">게시판</a>
	    		  </c:otherwise>	    		  
	 		</c:choose> 
	 		<c:if test="${user.admin eq 1}">
					<a href="${contextPath}/product/addProductForm.do" class="btn btn-secondary">상품 등록</a>
					<a href="javascript:adminChating()" class="btn btn-secondary">운영자채팅</a>
			</c:if>
		</div>
	</div>
</nav>