create table ordem_of_service (
    id bigint not null,
    client_id bigint not null,
    description text not null,
    price decimal(10, 2) not null,
    status varchar(20) not null,
    open_date timestamp  not null,
    finish_date timestamp,
    constraint ordem_of_service_pkey primary key(id)
);

alter table ordem_of_service add constraint fk_ordem_of_service_cliente
foreign key (cliente_id) references cliente (id);