<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>chatting</title>
</head>
<body>
	<h2>운영자와의 채팅</h2>
	<form>
	<!-- 텍스트 박스에 채팅의 내용을 작성한다. -->
	<input id="textMessage" type="text" onkeydown="return enter()">
	<!--메시지 전송 버튼 -->
	<input onclick="sendMessage()" value="보내기" type="button">
	</form> <br />
	<textarea id="messageTextArea" rows="10" cols="50" disabled="disabled"></textarea>
	
	<script type="text/javascript">
	// 톰캣에서 지정한 포트번호 입력
	var webSocket = new WebSocket("ws://localhost:8080/broadsocket");
	// 콘솔 텍스트 영역
	var messageTextArea = document.getElementById("messageTextArea");
	webSocket.onopen = function(message) {
	messageTextArea.value += "서버 연결완료...\n";
	};
	
	webSocket.onclose = function(message) { };
	// 에러가 발생하면
	webSocket.onerror = function(message) {
		// 콘솔에 메시지를 남긴다.
		messageTextArea.value += "error...\n";
	};
	// 서버(운영자)로부터 메시지가 도착하면 콘솔 화면에 메시지를 남긴다.
	webSocket.onmessage = function(message) {
		messageTextArea.value += "(운영자) => " + message.data + "\n";
	};
	
	function sendMessage() {
		// 텍스트 박스의 객체를 가져옴
		let message = document.getElementById("textMessage");
		// 콘솔에 메세지를 남긴다.
		messageTextArea.value += "(손님) => " + message.value + "\n";
		// 소켓으로 보낸다.
		webSocket.send(message.value);
		// 텍스트 박스 추기화
		message.value = "";
	}
	// 텍스트 박스에서 엔터를 누르면
	function enter() {
		if(event.keyCode === 13) {
			// 서버로 메시지 전송
			sendMessage();
			return false;
		}
	return true;
	}
	</script>
</body>
</html>