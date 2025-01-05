INSERT INTO orders (order_number, total)
VALUES ('ORD001', 75.50),
       ('ORD002', 150.00),
       ('ORD003', 250.25);


INSERT INTO order_lines (order_id, description, price)
VALUES (1, 'Product A', 20.00),
       (1, 'Product B', 30.50),
       (1, 'Product C', 25.00);

INSERT INTO order_lines (order_id, description, price)
VALUES (2, 'Product D', 50.00),
       (2, 'Product E', 25.00),
       (2, 'Product F', 20.00),
       (2, 'Product G', 30.00),
       (2, 'Product H', 25.00);


INSERT INTO order_lines (order_id, description, price)
VALUES (3, 'Product I', 100.00),
       (3, 'Product J', 150.25);