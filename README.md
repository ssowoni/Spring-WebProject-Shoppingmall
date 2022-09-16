# Spring-WebProject-Shoppingmall

환경
언어 : Java(Tm) SE Development Kit 11.0.13 
Server : Appache Tomcat 9.0 Tomcat9
DBMS : Oracle Database 11g Express Edition - sqldeveloper


1. 해당 계정 및 권한 부여
	1-1. cmd 실행
	1-2. sqlplus "/as sysdba"	입력
	1-3. create user webmarket identified by 1234;	입력
	1-4. grant resource, connect, dba, unlimited tablespace to webmarket;	입력
	1-5. commit;	입력

2. 새 DB 만들기
	2-1. sqldeveloper 실행
	2-2. Oracle 접속을 우클릭하여 새 접속 클릭
	2-3. Name에 WEBMARKET_DB, 사용자이름에 webmarket, 비밀번호에 1234 입력 후 저장

3. 이클립스-DB 연결
	3-1. Database Source Explorer 에서 Database Connections 우클릭하여 New 클릭
	3-2. Oracle 클릭 -> Next -> Drivers 바로 우측 버튼 클릭하여 New Driver Definition 진입
	3-3. System Version이 11인 Oracle Thin Driver 클릭 후 JAR List 탭 클릭
	3-4. ojdbc14.jar 클릭 이후 Edit JAR/Zip 클릭하여 워크스페이스 내부 webapp/resources/ojdbc6.jar 파일 선택
	3-5. Properties 탭 클릭 후 Connection URL의 db를 xe로 변경, Database Name의 db를 xe로 변경 후 OK
	3-6. Host 란에 localhost 입력, User name에 webmarket, Password에 1234 입력 후 Finish 클릭

4. 테이블 및 더미데이터 생성하기
	4-1. 생성한 DB Connect.
	4-2. import 한 파일의 webapp 폴더 내부의 sql 파일을 아래의 4-3의 순서대로 실행
	      순서대로 안할 시 foreign key로 인한 에러가 발생할 수 있음
	      순서대로 했는데 에러발생? -> Drop Table로 인한 에러
	4-3.
	      1) 제품테이블생성.sql, 제품초기데이터입력.sql
	      2) 회원테이블생성.sql, 회원초기데이터입력.sql
	      3) 카트테이블생성.sql
	      4) 배송정보테이블생성.sql
	      5) 게시판테이블생성.sql, 게시판초기데이터입력.sql, 게시판댓글테이블생성.sql

5. 이미지 준비
	5-1) resources/image에 존재하는 이미지 파일 3개를 C://upload 에 추가.

6. 서버 설정	
	6-1) Tomcat admin port : 8005
	       HTTP/1.1 : 8080
	6-2) KH_3rd_team_jsp_project의 Path
	       /KH_3rd_team_jsp_project 를  / 로 변경

7. 실행
	7-1) IE로 실행시 메인화면의 Map API가 적용되지 않으므로 Chrome, Whale, Edge 등 사용 권장.
	7-2) 메인화면인 welcome.jsp 실행 //  http://localhost:8080/welcome.jsp
	7-3) 로그인 초기(관리자) 데이터
	      ID : webmarket ,	Password : 1234
	      해당 계정이 아닌 다른 계정 생성 후 로그인 시
	      제품 등록 등 일부 기능 사용 불가능
	
	7-4) 채팅 : 서로 다른 브라우저에서 한 곳은 관리자계정(webmarket) 한 곳은 사용자계정(새로생성된계정)으로
		 로그인 후 우측상단의 채팅or 운영자채팅을 클릭하여 1:1 채팅이 가능.

8. 기능 나열
	: 구글맵 API  	 /  운영자-사용자 채팅
	  회원가입 	 /  로그인
	  제품 목록 확인	 /  제품 상세 확인  	    /  제품 추가
	  장바구니 추가  	 /  장바구니 일부 삭제  /  장바구니 모두 삭제
	  배송 정보 입력 	 /  배송 정보 확인        / 배송 내역 목록 확인  /  배송 내역 상세 확인
	  게시판 목록 확인 	 /  게시글 상세확인      / 게시글 수정  /  게시글 삭제  / 게시글 작성

