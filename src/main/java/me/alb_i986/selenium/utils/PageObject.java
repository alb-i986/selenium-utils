package me.alb_i986.selenium.utils;

public abstract class PageObject {

    protected final SeleniumHelper seleniumHelper;

    public PageObject(SeleniumHelper seleniumHelper) {
        this.seleniumHelper = seleniumHelper;
    }
}
