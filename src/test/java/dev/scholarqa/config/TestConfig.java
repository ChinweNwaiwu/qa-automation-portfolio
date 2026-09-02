package dev.scholarqa.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestConfig {
    private static final Properties PROPERTIES = loadProperties();

    private TestConfig() {
    }

    public static String uiBaseUrl() {
        return value("UI_BASE_URL", "ui.base.url");
    }

    public static String apiBaseUrl() {
        return value("API_BASE_URL", "api.base.url");
    }

    public static String username() {
        return value("UI_USERNAME", "ui.username");
    }

    public static String password() {
        return value("UI_PASSWORD", "ui.password");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(value("HEADLESS", "headless"));
    }

    public static long timeoutSeconds() {
        return Long.parseLong(value("TIMEOUT_SECONDS", "timeout.seconds"));
    }

    private static String value(String environmentKey, String propertyKey) {
        String systemValue = System.getProperty(propertyKey);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        String environmentValue = System.getenv(environmentKey);
        if (environmentValue != null && !environmentValue.isBlank()) {
            return environmentValue;
        }
        return PROPERTIES.getProperty(propertyKey);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties was not found on the test classpath");
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load test configuration", exception);
        }
    }
}
