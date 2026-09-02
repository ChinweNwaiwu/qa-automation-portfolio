package dev.scholarqa.ui.pages;

import dev.scholarqa.config.TestConfig;
import dev.scholarqa.ui.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(TestConfig.uiBaseUrl());
        visible(usernameInput);
        return this;
    }

    public InventoryPage loginSuccessfully(String username, String password) {
        submitCredentials(username, password);
        return new InventoryPage(driver).waitUntilLoaded();
    }

    public LoginPage loginExpectingFailure(String username, String password) {
        submitCredentials(username, password);
        visible(errorMessage);
        return this;
    }

    public String errorMessage() {
        return visible(errorMessage).getText();
    }

    private void submitCredentials(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        clickable(loginButton).click();
    }
}
