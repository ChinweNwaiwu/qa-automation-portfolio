INSERT INTO customers (customer_id, full_name, email, active) VALUES
    (1, 'Alice Mensah', 'alice@example.test', TRUE),
    (2, 'David Chen', 'david@example.test', TRUE),
    (3, 'Maria Silva', 'maria@example.test', FALSE);

INSERT INTO orders (order_id, customer_id, status, total_amount, created_at) VALUES
    (1001, 1, 'PAID', 149.99, TIMESTAMP '2026-08-01 09:30:00'),
    (1002, 1, 'SHIPPED', 49.99, TIMESTAMP '2026-08-02 10:15:00'),
    (1003, 2, 'CREATED', 75.00, TIMESTAMP '2026-08-03 12:00:00'),
    (1004, 2, 'CANCELLED', 20.00, TIMESTAMP '2026-08-04 16:45:00');
