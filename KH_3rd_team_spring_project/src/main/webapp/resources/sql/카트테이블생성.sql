drop sequence seq_cartId;
drop table carts;
create sequence seq_cartId increment by 1 start with 1;
create table carts(
	cartId number,
	userId varchar(20),
	productId varchar(20),
	productCount number default 1,
	totalPrice number default 0,
	primary key(cartId),
	foreign key (productId) references products(productId),
	foreign key (userId) references users(id)
);