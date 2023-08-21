CREATE TABLE meal
(
    meal_id       SERIAL        NOT NULL,
    name          VARCHAR(32)   NOT NULL,
    category      varchar(32) NOT NULL,
    description   TEXT,
    price         numeric(7, 2) not null,
    restaurant_id int           not null,
    image_name varchar(128),
    PRIMARY KEY (meal_id),
    CONSTRAINT fk_meal_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id)
);