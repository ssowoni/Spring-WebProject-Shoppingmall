<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../resources/css/bootstrap.css">  
<script type="text/javascript" src="../resources/js/write_validation.js"></script>
<title>자유 게시판</title>
<script>
function checkFile(f){

	// files 로 해당 파일 정보 얻기.
	var file = f.files;

	// file[0].name 은 파일명 입니다.
	// 정규식으로 확장자 체크
	if(!/\.(gif|jpg|jpeg|png)$/i.test(file[0].name)) alert('gif, jpg, png 파일만 선택해 주세요.\n\n현재 파일 : ' + file[0].name);

	// 체크를 통과했다면 종료.
	else return;

	// 체크에 걸리면 선택된  내용 취소 처리를 해야함.
	// 파일선택 폼의 내용은 스크립트로 컨트롤 할 수 없습니다.
	// 새로 폼을 새로 써주는 방식으로 초기화 합니다.
	f.outerHTML = f.outerHTML;
}
</script>
</head>
<body>
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3" style="text-align: center;">게시글 작성하기</h1>
	</div>
</div>

<div class="container" style="margin-top:60px">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card shadow">
				<div class="card-body">
					<form name="writeFrm" method="post" enctype="multipart/form-data" action="${contextPath }/board/addNewPost.do"  >
						<div class="form-group">
							<label for="board_subject">제목</label>
							<input type="text" name="title" id="title" value="" class="form-control" />
						</div>
						<div class="form-group">
							<label for="board_content">내용</label>
							<textarea name="content" id="content" class="form-control" rows="10" style="resize:none"></textarea>
						</div>
						<div class="form-group">
							<label for="board_file">첨부 이미지</label>
							<input type="file" name="image" class="form-control" onchange="checkFile(this)"/>					
						</div>
						<div class="form-group">
							<div class="text-right">
								<input type ="submit" class="btn btn-primary" value="작성완료" onclick="return CheckWriteForm(this)">
								<button type="reset" class="btn btn-info" >다시입력</button>
								<button type="button" class="btn btn-info" onclick="location.href='${contextPath }/board/listBoard.do';">목록보기</button>
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