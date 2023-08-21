CREATE TABLE "order"
(
    order_id            SERIAL                   NOT NULL,
    order_number        VARCHAR(64)              NOT NULL,
    status              varchar(32)             NOT NULL NOT NULL,
    received_date_time  TIMESTAMP WITH TIME ZONE NOT NULL,
    completed_date_time TIMESTAMP WITH TIME ZONE,
    restaurant_id       int                      NOT NULL,
    user_id             int                      NOT NULL,
    address_id          int                      NOT NULL,
    PRIMARY KEY (order_id),
    UNIQUE (order_number),
    CONSTRAINT fk_order_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id),
    CONSTRAINT fk_order_user
        FOREIGN KEY (user_id)
            REFERENCES "user" (user_id),
    CONSTRAINT fk_order_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);