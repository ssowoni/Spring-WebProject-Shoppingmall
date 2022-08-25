drop sequence seq_board_num;
drop table board;
create table board(
num number primary key, -- 게시글 번호
title VARCHAR2(200) not null, -- 게시글 제목
content varchar2(2000) not null, -- 게시글 내용
nickname varchar2(10) not null, -- 작성자 nickname
imagename varchar2(200), -- 이미지 첨부 파일 이름
postdate date default sysdate not null, -- 게시판 등록날짜, 작성일
commentcount number default 0 -- 댓글 개수 
);

--시퀀스 설정
create sequence seq_board_num
increment by 1  --증가치
--START WITH 1
minvalue 1
nomaxvalue
nocycle
nocache;
