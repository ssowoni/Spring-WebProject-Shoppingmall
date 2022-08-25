<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>chatting</title>
<style>
	/* 여러 채팅창 간의 간격과 배열 위치*/
	.float-left {
	float:left;
	margin: 5px;
	}
</style>
</head>
<body>
	<h2>손님과의 채팅</h2>
	<!-- 유저가 접속할 때마다 이 템플릿으로 채팅창을 생성한다. -->
	<div class="template" style="display:none">
		<form>
		<!-- 메시지 텍스트 -->
			<input type="text" class="message" onkeydown="if(event.keyCode === 13) return false;">
			<!-- 전송 버튼 -->
			<input value="보내기" type="button" class="sendBtn">
		</form>
		<br />
		<!-- 메시지가 표시되는 곳 -->
		<textarea rows="10" cols="50" class="console" disabled="disabled"></textarea>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script type="text/javascript">
	// 톰캣에서 지정한 포트번호 입력
		var webSocket = new WebSocket("ws://localhost:8080/admin");
		
		webSocket.onopen = function(message) { };
		webSocket.onclose = function(message) { };
		webSocket.onerror = function(message) { };
		// 서버로 부터 메시지가 오면
		webSocket.onmessage = function(message) {
			let node = JSON.parse(message.data);
			if(node.status === "visit") {
				let form = $(".template").html();
				form = $("<div class='float-left'></div>").attr("data-key",node.key).append(form);
				// body에 추가한다.
				$("body").append(form);
				// message는 유저가 메시지를 보낼 때 알려주는 메시지이다.
			} else if(node.status === "message") {
				// key로 해당 div영역을 찾는다.
				let $div = $("[data-key='"+node.key+"']");
				// console영역을 찾는다.
				let log = $div.find(".console").val();
				// 아래에 메시지를 추가한다.
				$div.find(".console").val(log + "(손님) => " +node.message + "\n");
				// bye는 유저가 접속을 끊었을 때 알려주는 메시지이다.
			} else if(node.status === "bye") {
			// 해당 키로 div를 찾아서 dom을 제거한다.
			$("[data-key='"+node.key+"']").remove();
			}
		};
	// 전송 버튼을 클릭하면 발생하는 이벤트
	$(document).on("click", ".sendBtn", function(){
		// div 태그를 찾는다.
		let $div = $(this).closest(".float-left");
		// 메시지 텍스트 박스를 찾아서 값을 취득한다.
		let message = $div.find(".message").val();
		// 유저 key를 취득한다.
		let key = $div.data("key");
		// console영역을 찾는다.
		let log = $div.find(".console").val();
		// 아래에 메시지를 추가한다.
		$div.find(".console").val(log + "(운영자) => " + message + "\n");
		// 텍스트 박스의 값을 초기화 한다.
		$div.find(".message").val("");
		// 웹소켓으로 메시지를 보낸다.
		webSocket.send(key+"#####"+message);
		});
		// 텍스트 박스에서 엔터키를 누르면
		$(document).on("keydown", ".message", function() {
			// keyCode 13은 엔터이다.
			if(event.keyCode === 13) {
			// 버튼을 클릭하는 트리거를 발생한다.
			$(this).closest(".float-left").find(".sendBtn").trigger("click");
			// form에 의해 자동 submit을 막는다.
			return false;
		}
		return true;
	});
</script>
</body>
</html>