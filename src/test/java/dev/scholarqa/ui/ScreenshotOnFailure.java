package dev.scholarqa.ui;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public class ScreenshotOnFailure implements AfterTestExecutionCallback {
    private final Supplier<WebDriver> driverSupplier;

    public ScreenshotOnFailure(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isEmpty()) {
            return;
        }
        WebDriver driver = driverSupplier.get();
        if (!(driver instanceof TakesScreenshot screenshotDriver)) {
            return;
        }
        byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
        String safeName = context.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        Path output = Path.of("screenshots", safeName + ".png");
        try {
            Files.createDirectories(output.getParent());
            Files.write(output, screenshot);
        } catch (IOException ignored) {
            // A screenshot must never hide the original test failure.
        }
    }
}
