package me.alb_i986.selenium.utils;

import org.openqa.selenium.WebDriver;

/**
 * A factory creating instances of {@link WebDriver}.
 */
public interface WebDriverFactory {
    WebDriver createDriver();
}
