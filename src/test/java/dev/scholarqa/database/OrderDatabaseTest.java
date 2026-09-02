package dev.scholarqa.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("database")
class OrderDatabaseTest {
    private Connection connection;

    @BeforeEach
    void resetData() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        connection = DatabaseTestSupport.openConnection();
        DatabaseTestSupport.resetDatabase(connection);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void customerOrderSummaryExcludesCancelledOrders() throws SQLException {
        String query = """
                SELECT COUNT(o.order_id) AS order_count,
                       COALESCE(SUM(o.total_amount), 0) AS total_value
                FROM customers c
                LEFT JOIN orders o
                    ON c.customer_id = o.customer_id
                    AND o.status <> 'CANCELLED'
                WHERE c.email = ?
                GROUP BY c.email
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "alice@example.test");
            try (ResultSet result = statement.executeQuery()) {
                assertThat(result.next()).isTrue();
                assertThat(result.getInt("order_count")).isEqualTo(2);
                assertThat(result.getBigDecimal("total_value"))
                        .isEqualByComparingTo(new BigDecimal("199.98"));
                assertThat(result.next()).isFalse();
            }
        }
    }

    @Test
    void everyOrderReferencesAnExistingCustomer() throws SQLException {
        String query = """
                SELECT COUNT(*) AS orphan_count
                FROM orders o
                LEFT JOIN customers c ON c.customer_id = o.customer_id
                WHERE c.customer_id IS NULL
                """;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            assertThat(result.next()).isTrue();
            assertThat(result.getInt("orphan_count")).isZero();
        }
    }

    @Test
    void databaseRejectsAnOrderForAnUnknownCustomer() {
        String insert = """
                INSERT INTO orders (order_id, customer_id, status, total_amount, created_at)
                VALUES (2001, 999, 'CREATED', 25.00, CURRENT_TIMESTAMP)
                """;

        assertThatThrownBy(() -> {
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                statement.executeUpdate();
            }
        }).isInstanceOf(SQLException.class)
          .hasMessageContaining("FK_ORDERS_CUSTOMER");
    }
}
