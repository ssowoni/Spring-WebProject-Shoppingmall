<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<html><head>
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css"/>
<title>주문 완료</title>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">주문 완료</h1>
		</div>
	</div>
	<div class="container">
		<h2 class="alert alert-danger">주문해주셔서 감사합니다.</h2>
		<p> 주문은<c:out value="${date}"/>에 배송될 예정입니다!
	</div>
	<div class="container">
		<p> <a href="${contextPath}/product/allProduct.do" class="btn btn-secondary"> &laquo; 상품 목록</a>
	</div>
</body>
</html>