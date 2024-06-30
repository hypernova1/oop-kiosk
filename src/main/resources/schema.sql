create table cart (
    id varchar(255) not null,
    user_id varchar(255) not null,
    primary key (id)
);
comment on table cart is '장바구니';
comment on column cart.user_id is '유저 아이디';

create table cart_item (
    quantity int not null,
    cart_id varchar(255),
    id varchar(255) not null,
    product_no varchar(255) not null,
    primary key (id)
);

comment on table cart_item is '장바구니 상품';
comment on column cart_item.quantity is '수량';
comment on column cart_item.product_no is '상품 번호';

create table "order" (
    order_no char(14) not null,
    user_id varchar(255) not null,
    primary key (order_no)
);

comment on table "order" is '주문 정보';
comment on column "order".order_no is '주문 번호';
comment on column "order".user_id is '주문자 유저 아이디';

create table order_item (
    product_price int not null,
    quantity int not null,
    id varchar(255) not null,
    "order_order_no" char(14),
    product_no varchar(255) not null,
    primary key (id)
);

comment on table order_item is '주문 상품';
comment on column order_item.product_price is '상품 가격';
comment on column order_item.quantity is '주문 수량';
comment on column order_item.product_no is '상품 번호';

create table payment (
    product_price int not null,
    shipping_price int not null,
    total_paid_price int not null,
    id varchar(255) not null,
    order_no varchar(14) not null,
    user_id varchar(255) not null,
    primary key (id)
);

comment on table payment is '결제 정보';
comment on column payment.product_price is '상품 가격';
comment on column payment.shipping_price is '배송비';
comment on column payment.total_paid_price is '결제 금액';
comment on column payment.order_no is '주문 번호';
comment on column payment.user_id is '유저 아이디';

create table product (
    price integer,
    name varchar(255),
    product_no varchar(255) not null,
    primary key (product_no)
);

comment on table product is '상품 정보';
comment on column product.price is '상품 가격';
comment on column product.name is '상품명';
comment on column product.product_no is '상품 번호';

create table stock (
   amount int not null,
   id varchar(255) not null,
   product_no varchar(255) unique,
   primary key (id)
);

comment on table stock is '상품 재고';
comment on column stock.amount is '수량';

create index idx_cart_user_id on cart (user_id);
create index idx_order_user_id on "order" (user_id);
create index idx_payment_user_id on payment (user_id);
create index idx_product_stock_product_no on stock (product_no);