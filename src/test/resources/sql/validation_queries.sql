-- Active customers and the total value of non-cancelled orders
SELECT c.email, COUNT(o.order_id) AS order_count, COALESCE(SUM(o.total_amount), 0) AS total_value
FROM customers c
LEFT JOIN orders o
    ON c.customer_id = o.customer_id
    AND o.status <> 'CANCELLED'
WHERE c.active = TRUE
GROUP BY c.email
ORDER BY c.email;

-- Data-integrity check: every order must have a valid customer
SELECT COUNT(*) AS orphan_count
FROM orders o
LEFT JOIN customers c ON c.customer_id = o.customer_id
WHERE c.customer_id IS NULL;
