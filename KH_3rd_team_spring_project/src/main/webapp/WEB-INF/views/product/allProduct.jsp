<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css"/> 
<title>상품 목록</title></head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">상품목록</h1>
		</div>
	</div>
	<div class="container">
		<div class="row" align="center">
			<c:forEach var="product" items="${productList}">
			<div class="col-md-4">
				<img src="${contextPath}/downloadProduct.do?imagename=${product.filename}" style="width: 100%">
				<h3>${product.pname}</h3>
				<p>${product.description}
				<p>${product.unitPrice}원
				<p><a href="${contextPath}/product/productDetails.do?id=${product.productId}" class="btn btn-secondary" role="button"> 상세 정보 &raquo;</a>
			</div>
			</c:forEach>
		</div>
		<hr>
	</div>
</body>
</html>