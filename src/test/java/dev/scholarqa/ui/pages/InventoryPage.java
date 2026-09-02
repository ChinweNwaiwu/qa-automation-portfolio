package dev.scholarqa.ui.pages;

import dev.scholarqa.ui.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {
    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By sortSelect = By.cssSelector("[data-test='product-sort-container']");
    private final By productPrices = By.cssSelector("[data-test='inventory-item-price']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage waitUntilLoaded() {
        visible(pageTitle);
        return this;
    }

    public String title() {
        return visible(pageTitle).getText();
    }

    public InventoryPage addProductToCart(String productSlug) {
        clickable(By.cssSelector("[data-test='add-to-cart-" + productSlug + "']")).click();
        return this;
    }

    public int cartCount() {
        return Integer.parseInt(visible(cartBadge).getText());
    }

    public CartPage openCart() {
        clickable(cartLink).click();
        return new CartPage(driver).waitUntilLoaded();
    }

    public InventoryPage sortByPriceLowToHigh() {
        new Select(visible(sortSelect)).selectByValue("lohi");
        return this;
    }

    public List<Double> displayedPrices() {
        return driver.findElements(productPrices).stream()
                .map(WebElement::getText)
                .map(text -> text.replace("$", ""))
                .map(Double::parseDouble)
                .toList();
    }
}
