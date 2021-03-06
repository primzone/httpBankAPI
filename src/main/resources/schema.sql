drop table if exists account CASCADE;
drop table if exists card CASCADE;
drop table if exists contractor CASCADE;
drop table if exists transaction CASCADE;
drop table if exists users_contractors CASCADE;
drop table if exists usr CASCADE;


create table account (
                         id BIGSERIAL not null,
                         account_number varchar(20) UNIQUE ,
                         balance float8 not null default (0),
                         contractor_id int8,
                         user_id int8 ,
                         primary key (id)
);

create table card (
                      id BIGSERIAL not null,
                      card_number varchar(19) UNIQUE,
                      confirmation boolean not null default (false),
                      account_id int8 not null ,
                      primary key (id)
);

create table contractor (
                            id BIGSERIAL not null,
                            name varchar(255),
                            corporation boolean not null default (false),
                            description varchar(1000),
                            primary key (id)
);

create table transaction (
                             id BIGSERIAL not null,
                             amount float8 not null,
                             confirmation boolean not null default (false),
                             sender_account_id int8 not null,
                             recipient_account_id int8 not null,
                             sender_card_number varchar(19),
                             recipient_card_number varchar(19),
                             transaction_number BIGSERIAL not null UNIQUE,
                             create_time timestamp,
                             primary key (id)
);

create table users_contractors (
                                   user_id int8 not null,
                                   contractor_id int8 not null,
                                   primary key (contractor_id, user_id)
);

create table usr (
                     id BIGSERIAL not null,
                     name varchar(255) not null,
                     lastname varchar(255),
                     primary key (id)
);


alter table if exists account
    add constraint UK_66gkcp94endmotfwb8r4ocxm9 unique (account_number);

alter table if exists account
    add constraint FK19gpofm6a1ys3iflxf6sx3b17
        foreign key (contractor_id) references contractor;

alter table if exists account
    add constraint FK28fcmglr2tde6x16oxmjor8ux
        foreign key (user_id) references usr;

alter table if exists card
    add constraint FK8v67eys6tqflsm6hrdgru2phu
        foreign key (account_id) references account;



alter table if exists transaction
    add constraint FKqg9hvuj0jh5jkd3ejm3ibfn10
        foreign key (sender_account_id) references account;

alter table if exists transaction
    add constraint FK53qo12unt0o5flr83axs6v2i7
        foreign key (recipient_account_id) references account;



alter table if exists users_contractors
    add constraint FKebq30piqkfsg2eu4jf1jtv3dx
        foreign key (contractor_id) references contractor;

alter table if exists users_contractors
    add constraint FKpra6v64nv90thyey4sun5ycg7
        foreign key (user_id) references usr;