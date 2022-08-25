drop table board_comment;
drop sequence seq_comment_num;
create table board_comment(
comment_num number primary key, -- 댓글 번호
comment_board_num number not null, -- 해당 게시글 번호
comment_content varchar2(500) not null, --댓글 내용
comment_nickname varchar2(10) not null, -- 댓글 작성자 nickname
comment_date date default sysdate not null --작성일
);


--외래키 설정
alter table board_comment
add constraint board_comment_fk
foreign key(comment_board_num) references board(num);


--시퀀스 설정
create sequence seq_comment_num
increment by 1  --증가치
--START WITH 1
minvalue 1
nomaxvalue
nocycle
nocache;