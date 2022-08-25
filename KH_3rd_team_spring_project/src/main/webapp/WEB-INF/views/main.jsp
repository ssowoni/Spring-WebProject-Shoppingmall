<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
<!-- 쇼핑몰 프로젝트 첫번째 시작페이지 만들기 -->
<!-- 날짜와 관련된 라이브러리 import -->
<!DOCTYPE html>
<html><head><meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<!-- bootstrap 스타일 활용 -->
<link rel="stylesheet" href="../resources/css/bootstrap.css">  
<title>Welcome</title>
</head>
<body>
	<!-- 문자열 데이터 입력 -->
	<%!
		String greeting = "웹 쇼핑몰에 오신 것을 환영합니다";
		String tagline = "Welcome to Web Market!";
	%>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">
				<%=greeting%>
			</h1>
		</div>
	</div>
	<div class="container">
		<div class="text-center">
			<h3><%=tagline%></h3>
			<%
				response.setIntHeader("Refresh",5);
				Date day = new Date();
				String am_pm;
				int hour = day.getHours();
				int minute = day.getMinutes();
				int second = day.getSeconds();
				if(hour/12 ==0){
					am_pm="AM";
				}else{
					am_pm= "PM";
					hour = hour-12;
				}
				String CT = hour +":"+minute+":"+second+" "+am_pm;
				out.println("현재 접속 시각: "+CT+"\n");
			%>
		</div>
	</div>
	<div class="text-center">
	<a href="${contextPath}/product/allProduct.do" class="btn btn-secondary">상품목록 살펴보기</a>
	</div>
	<!-- 외부파일 포함시킴 -->
	<%@ include file="map.jsp" %>
</body>
</html>