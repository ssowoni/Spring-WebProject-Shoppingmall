<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<html><head>
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css"/> 
<% request.setCharacterEncoding("utf-8"); %>
<title>주문정보</title>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">주문 정보</h1>
		</div>
	</div>
	
	<div class="container col-8 alert alert-light">
		<div class="text-center">
			<h1>영수증</h1>
		</div>
		
		<div class="row justify-content-between">
			<div class="col-4" align="left">
				성명: <c:out value="${orderData.name}"/><br>
				우편번호 : <c:out value="${orderData.zipCode}"/><br> 
				주소 : <c:out value="${orderData.address}"/><br>
				연락처 : <c:out value="${orderData.phoneNum}"/>
			</div>
			<div class="col-4" align="right">
				<p><em>배송일: <c:out value="${orderData.shipping_date}"/></em>
			</div>
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
			<c:forEach var="cartlist" items="${cartlist}">
					<c:set var="sum" value="${sum+cartlist.totalPrice}"/>
			<tr>
				<td class="text-center"><em>${cartlist.productId}</em></td>
				<td class="text-center">${cartlist.productCount}</td>
				<td class="text-center">${cartlist.totalPrice/cartlist.productCount}원</td>
				<td class="text-center">${cartlist.totalPrice}원</td>
			</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td class="text-right"><strong>총액: </strong></td>
				<td class="text-center text-danger"><strong><c:out value="${sum}"/></strong></td>
			</tr>
			</table>
			<table>
			<tr>
			<td><a href="${contextPath}/shipping/shippingForm.do" class="btn btn-secondary" role="button">이전</a></td>
			<td><form action="${contextPath}/shipping/addShipping.do" method="post">
			<input type="hidden" name="name" value="${orderData.name}">
			<input type="hidden" name="shipping_date" value="${orderData.shipping_date}>">
			<input type="hidden" name="zipCode" value="${orderData.zipCode}">
			<input type="hidden" name="address" value="${orderData.address}">
			<input type="hidden" name="phoneNum" value="${orderData.phoneNum}">
			<input type="submit" class="btn btn-success"value="주문 완료" >
			</form></td>
			<td><a href="${contextPath}/shipping/checkOutCancelled.do" class="btn btn-secondary" role="button">취소</a></td>
			</tr></table>
		</div>
	</div>
</body>
</html>