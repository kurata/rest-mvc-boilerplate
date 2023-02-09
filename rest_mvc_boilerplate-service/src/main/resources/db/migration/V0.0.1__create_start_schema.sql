CREATE TABLE customer
(
    id    BIGSERIAL    NOT NULL
        constraint pk_customer primary key,
    name  varchar(255) NOT NULL,
    birth date         NOT NULL,
    constraint uk_customer_name unique (name)
);
