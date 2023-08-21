CREATE TABLE "user"
(
    user_id                SERIAL                   NOT NULL,
    email                  VARCHAR(64)              NOT NULL,
    password  VARCHAR(128)  NOT NULL,
    name                   VARCHAR(32)              NOT NULL,
    surname                VARCHAR(32)              NOT NULL,
    phone                  VARCHAR(32)              NOT NULL,
    registration_date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    active    BOOLEAN       NOT NULL,
    address_id             INT,
    PRIMARY KEY (user_id),
    UNIQUE (email),
    UNIQUE (phone),
    CONSTRAINT fk_user_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);