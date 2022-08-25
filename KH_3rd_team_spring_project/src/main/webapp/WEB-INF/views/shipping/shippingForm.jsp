<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css"/> 
<title>배송 정보</title>
<c:set var="dataMap" value="${dataMap}"/>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">배송 정보</h1>
		</div>
	</div>
	<div class="container">
		<form action="${contextPath}/shipping/orderConfirmation.do" class="form-horizontal" method="post">
			<div class="form-group row">
				<label class="col-sm-2">수신자</label>
				<div class="col-sm-3">
					<input name="name" type="text" class="form-control" value="${dataMap.name}">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">배송일</label>
				<div class="col-sm-3">
					<input name="shipping_date" type="text" class="form-control" value="${dataMap.shipping_date}" readonly>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">연락처</label>
				<div class="col-sm-3">
					<input name="phoneNum" type="text" class="form-control" value="${dataMap.phoneNum}">(010-XXXX-XXXX)
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">우편번호</label>
				<div class="col-sm-3">
					<input name="zipCode" type="text" class="form-control" value="${dataMap.zipCode}">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">주소</label>
				<div class="col-sm-5">
					<input name="address" type="text" class="form-control" value="${dataMap.address}">
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-primary" value="등록"/>
					<a href="${contextPath}/cart/cartPage.do" class="btn btn-secondary" role="button">이전</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>