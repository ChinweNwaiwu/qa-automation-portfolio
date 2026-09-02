package dev.scholarqa.database;

import org.h2.tools.RunScript;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseTestSupport {
    private static final String JDBC_URL = "jdbc:h2:mem:portfolio;DB_CLOSE_DELAY=-1";

    private DatabaseTestSupport() {
    }

    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, "sa", "");
    }

    public static void resetDatabase(Connection connection) throws SQLException {
        runScript(connection, "sql/schema.sql");
        runScript(connection, "sql/test-data.sql");
    }

    private static void runScript(Connection connection, String resource) throws SQLException {
        var stream = DatabaseTestSupport.class.getClassLoader().getResourceAsStream(resource);
        if (stream == null) {
            throw new IllegalStateException("Missing SQL resource: " + resource);
        }
        RunScript.execute(connection, new InputStreamReader(stream, StandardCharsets.UTF_8));
    }
}
