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
<script type="text/javascript" src="../resources/js/userForm_validation.js"></script>
<title>Edit</title>
</head>
<body onload="loginCheck();">
	<div class="jumbotron">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<form method="post" action="${contextPath}/user/editUser.do" name="signFrm">
						<h3>Edit</h3>

						<input type="text" name="name" value="${user.name }"
							class="form-control input-lg" readonly >
						<input type="text" id="id" name="id" value=${user.id }
							class="form-control input-lg" readonly>
						<input type="password" id="pass" name="pass" value=""
							class="form-control input-lg" placeholder="Password">
						<input type="password" id="confirm_pass" name="confirm_pass" value=""
							class="form-control input-lg" placeholder="Confirm Password">
						<input type="text" id= "nickname" name="nickname" value=${user.nickname }
							class="form-control input-lg" placeholder="Nickname" readonly>
						<input type="text" id= "address" name="address" value="${user.address }"
							class="form-control input-lg" placeholder="Address, don't need to write it.">
						<input type="email" id="email" name="email" value=${user.email }
							class="form-control input-lg" placeholder="Email">
						<input type="text" id="phonenum" name="phonenum" value=${user.phonenum }
							class="form-control input-lg" placeholder="phone Number ex) 010-0000-0000">
						<input type ="submit" class="btn btn-primary" value="Edit my account" onclick="return CheckUserForm()">
					</form>
				</div>
			</div>
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