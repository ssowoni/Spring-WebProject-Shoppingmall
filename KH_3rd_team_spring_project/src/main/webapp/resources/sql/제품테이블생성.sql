DROP TABLE products CASCADE CONSTRAINTS;
create table products (
	productId varchar2(20) primary key, -- 상품 아이디
	pname varchar2(20) not null, -- 상품명
	unitPrice number not null, -- 상품 가격
	description varchar2(100) not null, -- 상품 설명
	manufacturer varchar2(20) not null, -- 제조사
	category varchar2(20) not null, -- 분류, 목록표(리스트)
	unitsInStock number default 0 not null, -- 재고수
	condition varchar2(20) not null, -- 신상품 or 중고품 or 재생품
	filename varchar2(100) default 0
);