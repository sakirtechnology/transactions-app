create table user(
userid int not null auto_increment,
email varchar2(30),
accountno long,
phonenumber long,
primary key(userid)
);

insert into user(email,  accountno, phonenumber) values ('user1@test.com',12341234,9999999999);
insert into user(email,  accountno, phonenumber) values ('user2@test.com',43123567,8888888888);
insert into user(email,  accountno, phonenumber) values ('user3@test.com',98192348,7777777777);

create table transaction
(
   transactionid varchar2(100) not null auto_increment,
   type varchar2(255) , 
   createddate date ,
   createdby int references user(userid),
   primary key(transactionid)
);



insert into transaction(type,  createddate, createdby) values ('bill',current_timestamp(),1);
insert into transaction(type,  createddate, createdby) values ('invoice',current_timestamp(),2);
insert into transaction(type,  createddate, createdby) values ('subscription',current_timestamp(),1);
insert into transaction(type,  createddate, createdby) values ('subscription',current_timestamp(),1);