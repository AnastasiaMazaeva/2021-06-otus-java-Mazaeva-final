-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)

create table dat_user (
    id bigserial not null primary key,
    login varchar(50),
    role varchar(50),
    password varchar(255),
    is_active boolean,
    is_open boolean,
    token uuid

);

create table document (
    id bigserial not null primary key ,
    name varchar(255),
    owner bigserial,
    description varchar(255),
     constraint document_user_fk foreign key(owner) references dat_user (id)
);

create table permission (
    id bigserial primary key ,
    document bigserial,
    receiver bigserial,
    constraint permission_user_fk foreign key (receiver) references dat_user(id),
    constraint permission_document_fk foreign key (document) references document(id)
)


