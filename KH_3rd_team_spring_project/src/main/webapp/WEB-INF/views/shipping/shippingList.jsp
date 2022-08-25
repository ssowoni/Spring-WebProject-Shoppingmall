<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<html><head>
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css"/> 
<title>배송 내역</title></head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3"  style="text-align: center;">배송 내역</h1>
		</div>
	</div>
	
	<div class="container col-8 alert alert-light">
		<div>
			<table class="table table-hover">
			<tr>
				<th class="text-center">주문번호</th>
				<th class="text-center">상품</th>
				<th class="text-center">개수</th>
				<th class="text-center">배송일</th>
				<th class="text-center">비고</th>
			</tr>
			<c:forEach var="shipping" items="${shippingList}" varStatus="status">
			<tr>
				<td class="text-center">
					<c:if test="${status.index eq 0}"> <c:out value="${shipping.shippingId}"/> </c:if>
					<c:if test="${status.index ne 0}">
						<c:if test="${shippingList[status.index-1].shippingId ne shipping.shippingId}">
							<c:out value="${shipping.shippingId}"/>
						</c:if>
					</c:if>
				</td>
				
				<td class="text-center"><em><c:out value="${shipping.productId}"/></em></td>
				<td class="text-center"><c:out value="${shipping.productCount}"/></td>
				<td class="text-center"><c:out value="${shipping.shipping_date}"/></td>
				
				<td class="text-center">
					<c:if test="${status.last}">
						<a href="${contextPath}/shipping/shippingView.do?shippingId=${shipping.shippingId}" role="button">상세 보기</a>
					</c:if>
					<c:if test="${!status.last}">
						<c:if test="${shippingList[status.index+1].shippingId ne shipping.shippingId}">
							<a href="${contextPath}/shipping/shippingView.do?shippingId=${shipping.shippingId}" role="button">상세 보기</a>
						</c:if>
					</c:if>
				</td>
			</tr>
			</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>