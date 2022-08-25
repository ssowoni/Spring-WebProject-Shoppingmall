function CheckUserForm(){
	var id = document.getElementById("id");
	var pass = document.getElementById("pass");
	var confirm_pass = document.getElementById("confirm_pass");
	var nickname = document.getElementById("nickname");
	var email = document.getElementById("email");
	var phoneNum = document.getElementById("phoneNum");
	
	if(id.value=="") {
		alert("아이디를 입력해주세요.");
		id.select();
		id.focus();
		return false;
	}
	else if(pass.value=="") {
		alert("비밀번호를 입력해주세요.");
		pass.select();
		pass.focus();
		return false;
	}
	else if(confirm_pass.value=="") {
		alert("confirm_pass에 비밀번호를 동일하게 다시 입력해주세요.");
		confirm_pass.select();
		confirm_pass.focus();
		return false;
	}
	
	else if(nickname.value=="") {
		alert("닉네임을 입력해주세요.");
		nickname.select();
		nickname.focus();
		return false;
	}
	else if(email.value=="") {
		alert("이메일을 입력해주세요.");
		email.select();
		email.focus();
		return false;
	}
	else if(phoneNum.value=="") {
		alert("핸드폰 번호를 입력해주세요.");
		phoneNum.select();
		phoneNum.focus();
		return false;
	}
	
	else if (pass.value!=confirm_pass.value){
		alert("비밀번호 입력이 동일하지 않습니다. 다시 입력해주세요.");
		pass.select();
		pass.focus();
		return false;
	}
	return true;
}
