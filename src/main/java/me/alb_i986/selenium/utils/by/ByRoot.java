package me.alb_i986.selenium.utils.by;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByRoot extends By {

    private final WebElement root;
    private final By by;

    public ByRoot(WebElement root, By by) {
        this.root = root;
        this.by = by;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return root.findElements(by);
    }

    @Override
    public String toString() {
        return String.format("By.root: %s %s", root, by);
    }
}
