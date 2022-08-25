function CheckWriteForm(){
	var title = document.getElementById("title");
	var content = document.getElementById("content")
	
	if(title.value=="") {
		alert("제목을 입력해주세요.");
		title.select();
		title.focus();
		return false;
	}
	else if(content.value=="") {
		alert("내용 입력해주세요.");
		content.select();
		content.focus();
		return false;
	}
	return true;
}
