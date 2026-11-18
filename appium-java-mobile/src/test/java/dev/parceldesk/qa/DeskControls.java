package dev.parceldesk.qa;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;

abstract class DeskControls {
    protected final WebDriverWait wait;

    DeskControls(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        wait.ignoring(StaleElementReferenceException.class);
    }

    protected final void click(String id) {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(id))).click();
    }

}
