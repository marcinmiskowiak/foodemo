CREATE TABLE restaurant
(
    restaurant_id          SERIAL                   NOT NULL,
    name                   VARCHAR(32)              NOT NULL,
    phone                  VARCHAR(32)              NOT NULL,
    registration_date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    owner_id               INT                      NOT NULL,
    address_id             INT                      NOT NULL,
    PRIMARY KEY (restaurant_id),
    CONSTRAINT fk_restaurant_restaurant_owner
        FOREIGN KEY (owner_id)
            REFERENCES "user" (user_id),
    CONSTRAINT fk_restaurant_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)

);