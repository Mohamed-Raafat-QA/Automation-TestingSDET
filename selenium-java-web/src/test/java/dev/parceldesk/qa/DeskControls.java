package dev.parceldesk.qa;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

abstract class DeskControls {
    protected final WebDriverWait wait;

    DeskControls(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        wait.ignoring(StaleElementReferenceException.class);
    }

    protected final void click(String id) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
    }

}
