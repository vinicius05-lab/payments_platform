create table users(
    id bigint primary key not null auto_increment,
    first_name varchar not null,
    last_name varchar not null,
    document varchar not null unique,
    email varchar not null unique,
    password varchar not null,
    balance decimal(10, 2),
    role varchar(5) not null,
    user_type varchar(8) not null
);

create table transactions(
    id bigint primary key not null auto_increment,
    amount decimal(10,2) not null,
    sender_id bigint not null,
    receiver_id bigint not null,
    transaction_date timestamp not null,
    foreign key(sender_id) references users(id),
    foreign key(receiver_id) references users(id)
);