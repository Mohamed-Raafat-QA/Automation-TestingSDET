package dev.parceldesk.qa;

import java.nio.file.Files;
import java.nio.file.Path;
import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class DeskFixture {
    protected WebDriver driver;
    protected DeskPage desk;

    @BeforeMethod(alwaysRun = true)
    public void start() throws Exception {
        var options = new ChromeOptions();
        String binary = System.getenv("CHROME_BIN");
        if (binary != null && !binary.isBlank()) options.setBinary(binary);
        options.addArguments("--headless=new", "--window-size=1280,900");
        driver = new ChromeDriver(options);
        driver.get(Path.of("demo/index.html").toAbsolutePath().toUri().toString());
        desk = new DeskPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void finish(ITestResult result) throws Exception {
        if (driver == null) return;
        try {
            if (!result.isSuccess()) {
                var dir = Files.createDirectories(Path.of("reports/screenshots"));
                var file = dir.resolve(result.getMethod().getMethodName() + ".png");
                Files.write(file, ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            }
        } catch (java.io.IOException | RuntimeException error) {
            System.err.println("Screenshot capture failed for " + result.getName() + ": " + error);
        } finally {
            driver.quit();
            driver = null;
        }
    }
}
