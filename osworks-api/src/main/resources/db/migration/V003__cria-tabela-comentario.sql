create table comentario (
    id bigint not null,
    ordem_of_service_id bigint not null,
    description text not null,
    send_date timestamp not null,
    primary key(id)
);

alter table comentario add constraint fk_comentario_ordem_of_service
foreign key (ordem_of_service_id) references ordem_of_service (id);