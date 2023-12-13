create table bookmark (
    id bigint not null auto_increment,
    title varchar(255) not null,
    url varchar(255) not null,
    created_at timestamp,
    primary key (id)
);
