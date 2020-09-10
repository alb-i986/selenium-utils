package me.alb_i986.selenium.utils.by;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Static factory methods for creating custom By instances.
 */
public class ByBy {

    private ByBy() {}

    public static By root(WebElement root, By by) {
        return new ByRoot(root, by);
    }
}
