create database bill;

create table bill.userinfo(

    id int primary key auto_increment,
    username varchar(255),
    password varchar(255),
    power varchar(255)
);

create table bill.goods(

    id int primary key auto_increment,
    imgurl varchar(255),
    detail varchar(255),
    url varchar(255)
);

create table bill.cate(

    id int auto_increment primary key,
    classify varchar(255),
    name varchar(255)

);