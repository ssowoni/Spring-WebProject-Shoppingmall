<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../resources/css/bootstrap.css">
<title>Welcome</title>
<c:choose>
	<c:when test="${result=='loginFailed'}">
		<script>
			window.onload=function(){
				alert("아이디나 비밀번호가 틀립니다. 다시 로그인 하세요!");
			}
		</script>
	</c:when>
</c:choose>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<form name="loginFrm" action="${contextPath}/user/login.do" method="post">
				<h1 class="h3 mb-3 fw-normal">Please sign in</h1>
				<div class="form-floating">
					<input type="text" class="form-control" id="id" name="id"
						placeholder="ID"> <label for="id">ID</label>
				</div>
				<div class="form-floating">
					<input type="password" class="form-control" id="pass" name="pass"
						placeholder="Password"> <label for="pass">Password</label>
				</div>
				<button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
			</form>
		</div>
	</div>
	<div class="container">
		<div class="text-center">
			<h3></h3>
		</div>
	</div>
	<div class="text-center"></div>
</body>
</html>