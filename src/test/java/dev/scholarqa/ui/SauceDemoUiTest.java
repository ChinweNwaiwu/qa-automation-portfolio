package dev.scholarqa.ui;

import dev.scholarqa.config.TestConfig;
import dev.scholarqa.ui.pages.CartPage;
import dev.scholarqa.ui.pages.InventoryPage;
import dev.scholarqa.ui.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
class SauceDemoUiTest {
    private WebDriver driver;

    @RegisterExtension
    final ScreenshotOnFailure screenshotOnFailure = new ScreenshotOnFailure(() -> driver);

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void standardUserCanLogInAndAddAProductToTheCart() {
        InventoryPage inventory = new LoginPage(driver)
                .open()
                .loginSuccessfully(TestConfig.username(), TestConfig.password());

        assertThat(inventory.title()).isEqualTo("Products");
        inventory.addProductToCart("sauce-labs-backpack");
        assertThat(inventory.cartCount()).isEqualTo(1);
        CartPage cart = inventory.openCart();

        assertThat(cart.itemNames()).containsExactly("Sauce Labs Backpack");
    }

    @Test
    void lockedOutUserSeesAUsefulErrorMessage() {
        LoginPage loginPage = new LoginPage(driver)
                .open()
                .loginExpectingFailure("locked_out_user", TestConfig.password());

        assertThat(loginPage.errorMessage())
                .contains("Sorry, this user has been locked out");
    }

    @Test
    void productsCanBeSortedFromLowestToHighestPrice() {
        InventoryPage inventory = new LoginPage(driver)
                .open()
                .loginSuccessfully(TestConfig.username(), TestConfig.password())
                .sortByPriceLowToHigh();

        List<Double> displayed = inventory.displayedPrices();
        List<Double> expected = new ArrayList<>(displayed);
        expected.sort(Comparator.naturalOrder());

        assertThat(displayed).isEqualTo(expected);
    }
}
