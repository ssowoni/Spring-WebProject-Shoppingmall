<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css"/> 
<title>상품 상세 정보</title>

<script type="text/javascript">
	function addToCart(){
		if (confirm("상품을 장바구니에 추가하시겠습니까?")) document.addForm.submit();
		else document.addForm.reset();
	}
</script>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">상품 정보</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class ="col-md-5">
			<img src="${contextPath}/downloadProduct.do?imagename=${product.filename}" style="width: 100%">
			</div>
			<div class="col-md-6">
				<h3>${product.pname}</h3>
				<p>${product.description}
				<p><b>상품 코드</b>:<span class="badge-danger">${product.productId}</span>
				<p><b>제조사</b> : ${product.manufacturer}
				<p><b>분류</b> : ${product.category}
				<p><b>재고 수</b> : ${product.unitsInStock}
				<h4>${product.unitPrice}원</h4>
				<p><form name="addForm" action="${contextPath}/cart/addCart.do?productId=${product.productId}&unitPrice=${product.unitPrice}" method="post">
					<a href="#" class="btn btn-info" onclick="addToCart()">상품 주문 &raquo;</a>
					<a href="${contextPath}/cart/cartPage.do" class="btn btn-warning">장바구니 &raquo;</a>
					<a href="${contextPath}/product/allProduct.do" class="btn btn-secondary"> 상품 목록 &raquo;</a>
				</form>
			</div>
		</div>
		<hr>
	</div>
</body>
</html>