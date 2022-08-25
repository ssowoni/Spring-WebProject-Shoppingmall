<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<html><head>
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css"/> 
<title>배송 내역 상세</title></head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">배송 내역</h1>
		</div>
	</div>
	<div class="container col-8 alert alert-light">
		<div class="text-center">
			<h1>영수증</h1>
		</div>
		<div class="row justify-content-between">
		<c:forEach var="shippingView" items="${shippingView}" end="0" >
			<div class="col-4" align="left">
				성명: <c:out value="${shippingView.name}"/><br>
				우편번호 : <c:out value="${shippingView.zipCode}"/><br> 
				주소 : <c:out value="${shippingView.address}"/><br>
				연락처 : <c:out value="${shippingView.phoneNum}"/>
			</div>
			<div class="col-4" align="right">
				<p><em>배송일: <c:out value="${shippingView.shipping_date}"/></em>
			</div>
			</c:forEach>
		</div>
		<div>
			<table class="table table-hover">
			<tr>
				<th class="text-center">상품</th>
				<th class="text-center">개수</th>
				<th class="text-center">가격</th>
				<th class="text-center">소계</th>
			</tr>
			<c:set var="sum" value="0"/>
			<c:forEach var="productList" items="${productList}" varStatus="status">
			<tr>
				<td class="text-center"><em>${productList.productId}</em></td>
				<td class="text-center">${shippingView[status.index].productCount}</td>
				<td class="text-center">${productList.unitPrice}원</td>
				<td class="text-center">${productList.unitPrice*shippingView[status.index].productCount}원</td>
			</tr>
			<c:set var="sum" value="${sum+(productList.unitPrice * shippingView[status.index].productCount)}"/>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td class="text-right"><strong>총액: </strong></td>
				<td class="text-center text-danger"><strong><c:out value="${sum}"/></strong></td>
			</tr>
			</table>
			<a href="${contextPath}/shipping/shippingList.do" class="btn btn-secondary" role="button">이전</a>
			<a href="${contextPath}/main.do" class="btn btn-secondary" role="button">홈 화면</a>
			<a href="${contextPath}/product/allProduct.do" class="btn btn-secondary" role="button">상품 목록</a>
		</div>
	</div>
</body>
</html>