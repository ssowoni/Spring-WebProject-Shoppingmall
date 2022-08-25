<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
  request.setCharacterEncoding("UTF-8");
%>   
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../resources/css/bootstrap.css">  
<script type="text/javascript" src="../resources/js/write_validation.js"></script>
<title>자유 게시판</title>

</head>
<body>

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3" style="text-align: center;">게시글 수정하기</h1>
	</div>
</div>

<div class="container" style="margin-top:60px">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card shadow">
				<div class="card-body">
					<form name="writeFrm" method="post" enctype="multipart/form-data" action="${contextPath }/board/modPost.do?num=${board.num}">
						<input type="hidden" name="num" value="${board.num }" /> 
    					<input type="hidden" name="imagename" value="${board.imagename }" />	
						<div class="form-group">
							<label for="board_subject">제목</label>
							<input type="text" name="title" id="title" value="${board.title }" class="form-control" />
						</div>
						<div class="form-group">
							<label for="board_content">내용</label>
							<textarea name="content" id="content" class="form-control" rows="10" style="resize:none">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label for="board_file">첨부 이미지</label>
							<input type="file" name="image" class="form-control" />					
						</div>
						<div class="form-group">
							<div class="text-right">
								<input type ="submit" class="btn btn-primary" value="수정완료" onclick="return CheckWriteForm(this)">
								<button type="reset" class="btn btn-info" >다시입력</button>
								<button type="button" class="btn btn-info" onclick="location.href='${contextPath}/board/listBoard.do';">목록보기</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</div>
</body>
</html>