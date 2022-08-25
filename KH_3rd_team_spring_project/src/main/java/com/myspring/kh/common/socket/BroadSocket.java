package com.myspring.kh.common.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;

@Controller
// 일반 유저에서 서버간의 웹 소켓 url
@ServerEndpoint("/broadsocket")
public class BroadSocket {
	// searchUser 함수의 filter 표현식을 위한 인터페이스
	private interface SearchExpression {
		// 람다식을 위한 함수
		boolean expression(User user);
	}

	private class User {
		Session session;
		String key;
	}

	// 유저와 서버간의 접속 리스트
	private static List<User> sessionUsers = Collections.synchronizedList(new ArrayList<>());

	// Session으로 접속 리스트에서 User 클래스를 탐색
	private static User getUser(Session session) {
		return searchUser(x -> x.session == session);
	}

	// Key로 접속 리스트에서 User 클래스를 탐색
	private static User getUser(String key) {
		return searchUser(x -> x.key.equals(key));
	}

	// 접속 리스트 탐색 함수
	private static User searchUser(SearchExpression func) {
		Optional<User> op = sessionUsers.stream().filter(x -> func.expression(x)).findFirst();
		if (op.isPresent()) {
			return op.get();
		}
		return null;
	}

	// browser에서 웹 소켓으로 접속하면 호출되는 함수
	@OnOpen
	public void handleOpen(Session userSession) {
		// 인라인 클래스 User를 생성
		User user = new User();
		// Unique키를 발급
		user.key = UUID.randomUUID().toString().replace("-", "");
		user.session = userSession;
		sessionUsers.add(user);
		Admin.visit(user.key);
	}

	// browser에서 웹 소켓을 통해 메시지가 오면 호출되는 함수
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		User user = getUser(userSession);
		if (user != null) {
			Admin.sendMessage(user.key, message);
		}
	}

// 운영자 client가 유저에게 메시지를 보내는 함수
	public static void sendMessage(String key, String message) {
		// key로 접속 리스트에서 User 클래스를 탐색
		User user = getUser(key);
		// 접속 리스트에 User가 있으면(당연히 있다. 없으면 버그..)
		if (user != null) {
			try {
				// 유저 Session으로 socket을 취득한 후 메시지를 전송한다.
				user.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// WebSocket이 종료가 되면, 종료 버튼이 없기 때문에 유저 브라우저가 닫히면 발생한다.
	@OnClose
	public void handleClose(Session userSession) {
		User user = getUser(userSession);
		if (user != null) {
			Admin.bye(user.key);
			sessionUsers.remove(user);
		}
	}

	public static String[] getUserKeys() {
		String[] ret = new String[sessionUsers.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = sessionUsers.get(i).key;
		}
		return ret;
	}
}
