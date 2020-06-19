create table cliente (
    id bigint not null,
    nome varchar(60) not null,
    email varchar(255) not null,
    phone varchar(20) not null,
    constraint cliente_pkey primary key (id)
);