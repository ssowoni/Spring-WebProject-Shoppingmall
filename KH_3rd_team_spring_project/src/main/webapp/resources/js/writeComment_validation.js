function CheckWriteCommentForm(){
	var comment_content = document.getElementById("comment_content");
	
	if(comment_content.value=="") {
		alert("댓글을 입력해주세요.");
		comment_content.select();
		comment_content.focus();
		return false;
	}
	return true;
}
