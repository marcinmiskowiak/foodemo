CREATE TABLE address
(
    address_id       SERIAL      NOT NULL,
    country          VARCHAR(32) NOT NULL,
    city             VARCHAR(32) NOT NULL,
    postal_code      VARCHAR(32) NOT NULL,
    street           VARCHAR(32) NOT NULL,
    building_number  VARCHAR(24) NOT NULL,
    apartment_number VARCHAR(24),
    PRIMARY KEY (address_id)
);
