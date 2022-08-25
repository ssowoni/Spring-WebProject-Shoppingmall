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
<script type="text/javascript" src="../resources/js/writeComment_validation.js"></script>
<title>회원제 게시판</title>
<script>
function deletePost() {
    var confirmed = confirm("게시글을 삭제하겠습니까?"); 
    if (confirmed) {
        var form = document.writeFrm;     
        form.method = "post";              
        form.action = "${contextPath}/board/removePost.do?num=${board.num}";  
        form.submit();                    
    }
}

function deleteComment() {
    var confirmed = confirm("댓글을 삭제하겠습니까?"); 
    if (confirmed) {
        var form = document.commentFrm;       
        form.method = "post";                
        form.action = "../webmarket/deletecomment.do";  
        form.submit();                      
    }
}


</script>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="text-align: center;">게시글 보기</h1>
		</div>
	</div>

<div class="container" style="margin-top:60px">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card shadow">
				<div class="card-body">
									
					<form name="writeFrm">
					    <input type="hidden" name="num" value="${board.num }" />  <!-- 공통 링크 -->
						<div class="form-group">
							<label for="board_writer_name">작성자</label>
							<input type="text" value="${board.nickname }" class="form-control" disabled="disabled"/>
						</div>
						<div class="form-group">
							<label for="board_date">작성날짜</label>
							<input type="text" value="${board.postdate }" class="form-control" disabled="disabled" />
						</div>
						<div class="form-group">
							<label for="board_subject">제목</label>
							<input type="text"  value="${board.title }" class="form-control" disabled="disabled" />
						</div>
						<div class="form-group">
							<label for="board_content">내용</label>
							<textarea class="form-control" rows="10" style="resize:none" disabled="disabled">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label for="board_file">첨부 이미지</label>
							<c:choose>
								<c:when test="${not empty board.imagename }">
								  <input  type= "hidden"   name="originalFileName" value="${board.imagename }" />
		    						<img src="${contextPath}/download.do?num=${board.num}&imagename=${board.imagename}" style="width: 80%"  /><br>
								</c:when>
								<c:otherwise>
								<input type="text"  value="첨부 파일 없음" class="form-control"/>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="form-group">
							<!--text-right : 태그 요소 우측 정렬-->
							<div class="text-right">
							
							<c:choose>
								<c:when test= "${not empty user.nickname and user.nickname eq board.nickname}" >
					      			<button type="button" class="btn btn-primary"
					                       onclick="location.href='${contextPath }/board/modPostForm.do?num=${board.num}';">
					                   수정하기</button>
					              <button type="button" class="btn btn-danger" onclick="deletePost();">삭제하기</button> 
					              </c:when>
					              <c:when test="${user.admin eq 1}">
					              	 <button type="button" class="btn btn-danger" onclick="deletePost();">삭제하기</button> 
					              </c:when>  
							</c:choose>	
							 <button type="button" class="btn btn-info" onclick="location.href='${contextPath}/board/listBoard.do';">목록보기</button> 
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</div><br>


<!-- 댓글 창 -->
<div class="container">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card shadow">
				<div class="card-body">
					<h5><b>댓글 창</b></h5>
					<c:forEach items="${commentLists }" var="row">
						<form name="commentFrm">
						<div class="form-group">
					
						<input type="hidden" name="comment_num" value="${row.comment_num }" />
						<input type="hidden" name="comment_board_num" value="${ row.comment_board_num}" />
						${row.comment_nickname }
						<%-- <c:if test="${(not empty user.nickname and (user.nickname eq row.comment_nickname)) or user.admin eq 1} "> --%>
						<c:if test="${user.admin eq 1 or not empty user.nickname and user.nickname eq row.comment_nickname}">
						<a onclick="return confirm('정말로 삭제하시겠습니까?')" href = "${contextPath }/board/removeComment.do?comment_board_num=${row.comment_board_num }&comment_num=${row.comment_num}" class="btn-primary">삭제</a>
						</c:if>

						<textarea class="form-control" rows="3" style="resize:none" disabled="disabled">${row.comment_content}</textarea>
						</div>
					</form>
					</c:forEach>		
					<hr>
					<h5><b>댓글 등록</b></h5>
					
					<form name="writeCommentFrm" method="post"  action="${contextPath }/board/addNewComment.do" enctype="Multipart/form-data" >
						<input type="hidden" name="comment_board_num" value="${board.num }" />
						<input type="hidden" name="comment_nickname" value="${user.nickname }" />
						<div class="form-group">
							<label for="board_content">${user.nickname }</label>
							<textarea class="form-control" name="comment_content" rows="3" style="resize:none" ></textarea>
						</div>
						
						<div class="form-group">
							<div class="text-right">
								<input type ="submit" class="btn btn-primary" value="댓글등록" onclick="return CheckWriteCommentForm(this)">
							</div>
						</div>
					</form>
					
					
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>