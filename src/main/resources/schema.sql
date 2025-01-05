DROP TABLE IF EXISTS order_lines CASCADE;
DROP TABLE IF EXISTS orders;

CREATE TABLE orders
(
    id           BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(255) NOT NULL,
    total        DECIMAL(10, 2) DEFAULT 0.00
);

CREATE TABLE order_lines
(
    id          BIGSERIAL PRIMARY KEY,
    order_id    BIGINT         NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    description VARCHAR(255)   NOT NULL,
    price       DECIMAL(10, 2) NOT NULL
);
