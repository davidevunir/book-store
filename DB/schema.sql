CREATE DATABASE CLIENT_DB;

CREATE TABLE IF NOT EXISTS CLIENT
(
    ID_CLIENT  uuid         NOT NULL DEFAULT gen_random_uuid(),
    FIRST_NAME varchar(50)  NOT NULL,
    LAST_NAME  varchar(50)  NOT NULL,
    EMAIL      varchar(100) NOT NULL,
    ADDRESS    varchar(100),
    PHONE      varchar(20),
    ACTIVE     boolean      NOT NULL DEFAULT true,
    CREATED_AT timestamp    NOT NULL DEFAULT current_timestamp,
    CONSTRAINT PK_CLIENT PRIMARY KEY (ID_CLIENT),
    CONSTRAINT UK_CLIENT_EMAIL UNIQUE (EMAIL)
);

CREATE DATABASE CATALOG_DB;

CREATE TABLE IF NOT EXISTS BOOK
(
    ID_BOOK      uuid          NOT NULL DEFAULT gen_random_uuid(),
    TITLE        varchar(100)  NOT NULL,
    AUTHOR       varchar(100)  NOT NULL,
    ISBN         varchar(17),
    SYNOPSIS     varchar(255),
    RATING       numeric(2, 1) CHECK (RATING >= 1 AND RATING <= 5),
    PRICE        numeric(6, 2) NOT NULL,
    PUBLISHED_AT timestamp,
    STOCK        numeric(7),
    ACTIVE       boolean DEFAULT true,
    CONSTRAINT PK_BOOK PRIMARY KEY (ID_BOOK),
    CONSTRAINT UK_BOOK_TITLE_AUTHOR UNIQUE (TITLE, AUTHOR)
);

CREATE DATABASE ORDER_DB;

CREATE TABLE IF NOT EXISTS BOOK_ORDER
(
    ID_BOOK_ORDER   uuid          NOT NULL DEFAULT gen_random_uuid(),
    ID_CLIENT       uuid          NOT NULL,
    CREATED_AT      timestamp     NOT NULL DEFAULT current_timestamp,
    TOTAL_AMOUNT    numeric(10,2) NOT NULL,
    CONSTRAINT PK_BOOK_ORDER PRIMARY KEY (ID_BOOK_ORDER)
);

CREATE TABLE IF NOT EXISTS ORDER_DETAIL
(
    ID_ORDER_DETAIL uuid          NOT NULL DEFAULT gen_random_uuid(),
    ID_BOOK_ORDER   uuid          NOT NULL,
    ID_BOOK         uuid          NOT NULL,
    QUANTITY        integer       NOT NULL,
    PRICE           numeric(6,2)  NOT NULL,
    SUBTOTAL        numeric(8,2)  NOT NULL,
    CONSTRAINT PK_ORDER_DETAIL PRIMARY KEY (ID_ORDER_DETAIL),
    CONSTRAINT FK_ORDER_DETAIL_ORDER FOREIGN KEY (ID_BOOK_ORDER) REFERENCES BOOK_ORDER(ID_BOOK_ORDER)
);