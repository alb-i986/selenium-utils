package me.alb_i986.selenium.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SeleniumHelper {

    public static final int DEFAULT_WAIT_TIMEOUT = 20;

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SeleniumHelper(WebDriver driver) {
        this(driver, DEFAULT_WAIT_TIMEOUT);
    }

    public SeleniumHelper(WebDriver driver, long waitTimeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, waitTimeout);
    }

    public void click(By by) {
        waitUntil(elementToBeClickable(by))
                .click();
    }

    public void enterText(By by, String text) {
        waitUntil(visibilityOfElementLocated(by))
                .sendKeys(text);
    }

    public void selectByVisibleText(By by, String option) {
        Select select = getSelect(by);
        select.selectByVisibleText(option);
    }

    public void selectByValue(By by, String optionValue) {
        Select select = getSelect(by);
        select.selectByValue(optionValue);
    }

    private Select getSelect(By by) {
        WebElement selectElement = waitUntil(visibilityOfElementLocated(by));
        return new Select(selectElement);
    }

    public FrameHelper frameHelper() {
        return new FrameHelper();
    }

    public JavascriptHelper javascriptHelper() {
        return new JavascriptHelper();
    }

    public <T> T waitUntil(Function<? super WebDriver, T> expectedCondition) {
        return wait.until(expectedCondition);
    }

    /**
     * Wait with a customizable timeout.
     *
     * @see WaitHelper#forMaxSeconds(long)
     */
    public WaitHelper waitt() {
        return new WaitHelper();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public class WaitHelper {

        private long customWaitTimeout = waitTimeout;

        private WaitHelper() {}

        public WaitHelper forMaxSeconds(long seconds) {
            this.customWaitTimeout = seconds;
            return this;
        }

        public <T> T until(Function<? super WebDriver, T> expectedCondition) {
            return new WebDriverWait(driver, customWaitTimeout)
                    .until(expectedCondition);
        }
    }

    public class JavascriptHelper {

        private JavascriptHelper() {}

        public JavascriptHelper scrollToTop() {
            scrollTo(0, 0);
            return this;
        }

        public JavascriptHelper scrollTo(int x, int y) {
            executeJavascript(String.format("window.scrollTo(%d, %d)", x, y));
            return this;
        }

        public JavascriptHelper scrollBy(int x, int y) {
            executeJavascript(String.format("window.scrollBy(%d, %d)", x, y));
            return this;
        }

        public JavascriptHelper scrollToElementById(String elementId) {
            executeJavascript(String.format("document.getElementById(\"%s\").scrollIntoView();", elementId));
            return this;
        }

        public JavascriptHelper executeJavascript(String js, Object... args) {
            ((JavascriptExecutor) driver).executeScript(js, args);
            return this;
        }
    }

    public class FrameHelper {

        private FrameHelper() {}

        public void switchToFirstIframe() {
            switchToFrame(By.tagName("iframe"));
        }

        public void switchToFrame(By frameLocator) {
            wait.until(frameToBeAvailableAndSwitchToIt(frameLocator));
        }
    }
}
