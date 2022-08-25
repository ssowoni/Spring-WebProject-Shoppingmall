DROP TABLE shippingInfo CASCADE CONSTRAINTS;
drop sequence seq_shippingId;
create sequence seq_shippingId increment by 1 start with 1;
create table shippingInfo (
	shippingId number not null,
	userId varchar(20) not null,
	name varchar(10) not null,
	shipping_date date not null,
	productId varchar(20) not null,
	productCount number not null,
	address varchar(50) not null,
	zipCode varchar(20) not null,
	phoneNum varchar2(13) not null,
	foreign key (userId) references users(id),
	foreign key (productId) references products(productId)
);