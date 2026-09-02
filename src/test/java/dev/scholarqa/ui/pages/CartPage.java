package dev.scholarqa.ui.pages;

import dev.scholarqa.ui.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CartPage extends BasePage {
    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By itemNames = By.cssSelector("[data-test='inventory-item-name']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage waitUntilLoaded() {
        visible(pageTitle);
        return this;
    }

    public List<String> itemNames() {
        return driver.findElements(itemNames).stream().map(element -> element.getText()).toList();
    }
}
