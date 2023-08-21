CREATE TABLE meal_order
(
    meal_order_id SERIAL        NOT NULL,
    quantity      int           not null,
    price         numeric(5, 2) not null,
    meal_id       int           not null,
    order_id      int           not null,
    PRIMARY KEY (meal_order_id),
    CONSTRAINT fk_meal_order_meal
        FOREIGN KEY (meal_id)
            REFERENCES meal (meal_id),
    CONSTRAINT fk_meal_order_order
        FOREIGN KEY (order_id)
            REFERENCES "order" (order_id)


);