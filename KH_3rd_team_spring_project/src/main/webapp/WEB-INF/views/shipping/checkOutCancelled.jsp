<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<html><head>
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css"/> 
<title>주문 취소</title>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">주문취소</h1>
		</div>
	</div>
	<div class="container">
		<h2 class="alert alert-danger">주문이 취소되었습니다.</h2>
	</div>
	<div class="container">
		<p><a href="${contextPath}/product/allProduct.do" class="btn btn-secondary"> &laquo; 상품 목록</a>
	</div>
</body>
</html>