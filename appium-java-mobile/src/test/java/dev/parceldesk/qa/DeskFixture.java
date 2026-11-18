package dev.parceldesk.qa;

import java.nio.file.Files;
import java.nio.file.Path;
import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.net.URI;
import java.util.Locale;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public abstract class DeskFixture {
    protected WebDriver driver;
    protected DeskScreen desk;

    @BeforeMethod(alwaysRun = true)
    public void start() throws Exception {
        var server = URI.create(System.getenv().getOrDefault("APPIUM_URL", "http://127.0.0.1:4723")).toURL();
        String app = Path.of(required("APP_PATH")).toRealPath().toString();
        String udid = required("DEVICE_UDID");
        String name = System.getenv().getOrDefault("DEVICE_NAME", "Parcel Desk device");
        driver = switch (required("PLATFORM").toLowerCase(Locale.ROOT)) {
            case "android" -> new AndroidDriver(server, new UiAutomator2Options()
                .setDeviceName(name).setUdid(udid).setApp(app).setNoReset(false));
            case "ios" -> new IOSDriver(server, new XCUITestOptions()
                .setDeviceName(name).setUdid(udid).setApp(app).setNoReset(false));
            default -> throw new IllegalArgumentException("PLATFORM must be Android or iOS");
        };
        desk = new DeskScreen(driver);
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
        } finally {
            driver.quit();
            driver = null;
        }
    }

    private static String required(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Set " + name);
        return value;
    }
}
