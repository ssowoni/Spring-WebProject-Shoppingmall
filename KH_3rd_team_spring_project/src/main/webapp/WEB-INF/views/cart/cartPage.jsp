<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css"/> 
<title>장바구니</title>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">장바구니</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<table width="100%">
				<tr>
					<td align="left"> <a href="${contextPath}/cart/deleteCart.do" class="btn btn-danger">삭제하기</a></td>
					<td align="right"><a href="${contextPath}/shipping/shippingForm.do" class="btn btn-success">주문하기</a></td>
				</tr>
			</table>
		</div>
		<div style="padding-top: 50px">
			<table class="table table-hover">
				<tr>
					<th>상품</th>
					<th>가격</th>
					<th>수량</th>
					<th>소계</th>
					<th>비고</th>
				</tr>
				<c:set var = "sum" value="0"/>
				<c:forEach var="cartlist" items="${cartlist}">
					<c:set var="sum" value="${sum+cartlist.totalPrice}"/>
				<tr>
					<td>${cartlist.productId}</td>
					<td>${cartlist.totalPrice/cartlist.productCount}</td>
					<td>${cartlist.productCount}</td>
					<td>${cartlist.totalPrice}</td>
					<td><a href="${contextPath}/cart/removeCart.do?productId=${cartlist.productId}"class="badge badge-danger">삭제</a></td>
				</tr>
				</c:forEach>
				<tr>
					<th></th>
					<th></th>
					<th>총액</th>
					<th><c:out value="${sum}"/></th>
					<th></th>
				</tr>
			</table>
			<a href="${contextPath}/product/allProduct.do" class="btn btn-secondary"> &laquo; 쇼핑 계속하기</a>
		</div>
		<hr>
	</div>
</body>
</html>