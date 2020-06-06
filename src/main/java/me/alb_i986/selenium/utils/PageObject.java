package me.alb_i986.selenium.utils;

import org.openqa.selenium.WebDriver;

public abstract class PageObject {

    protected final SeleniumHelper seleniumHelper;
    protected final WebDriver driver;

    public PageObject(SeleniumHelper seleniumHelper) {
        this.seleniumHelper = seleniumHelper;
        this.driver = seleniumHelper.getDriver();
    }
}
